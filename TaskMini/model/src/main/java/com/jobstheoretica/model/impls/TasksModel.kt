package com.jobstheoretica.model.impls

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.jobstheoretica.entity.bindable.Messenger
import com.jobstheoretica.entity.bindable.Task
import com.jobstheoretica.entity.bindable.Trash
import com.jobstheoretica.model.interfaces.ITasksModel
//import com.jobstheoretica.realmdao.dependencies.Dependency
//import com.jobstheoretica.realmdao.interfaces.IDao
import com.jobstheoretica.restdao.dependencies.Dependency
import com.jobstheoretica.restdao.interfaces.IDao
import kotlinx.coroutines.experimental.*
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

internal class TasksModel:ITasksModel {

    private val myDao = Dependency(IDao::class).inject()

    override val parentJob: Job = Job()

    private val _taskLiveData = MutableLiveData<Task>()
    override val taskLiveData: LiveData<Task>
        get() = this._taskLiveData

    private val _tasksLiveData = MutableLiveData<List<Task>>()
    override val tasksLiveData: LiveData<List<Task>>
        get() = this._tasksLiveData

    private val _messengerLiveData = MutableLiveData<Messenger>()
    override val messengerLiveData: LiveData<Messenger>
        get() = this._messengerLiveData

    private val _debugModelMsgLiveData = MutableLiveData<String>()
    override val debugModelMsgLiveData:LiveData<String>
        get() = this._debugModelMsgLiveData

    override suspend fun readAsync(condition: String?): Deferred<Unit> = async(parent = this.parentJob) {
        val tasks = myDao.read(Task::class)
        if(tasks != null){
            _tasksLiveData.postValue(tasks)
        }
    }

    override suspend fun removeAsync(id: String): Deferred<Unit> = async(parent = this.parentJob) {
        val task = myDao.read(Task::class).singleOrNull{
            it.id == id
        }

        if(task != null){
            createTrashAsync(task).await()
            deleteTaskAsync(task).await()
        }
    }
    private suspend fun createTrashAsync(task: Task):Deferred<Unit> = async(parent = this.parentJob) {
        val trash = Trash()
        for(trashProp in trash::class.declaredMemberProperties){
            trashProp.javaField?.isAccessible = true
            val taskProp = task::class.declaredMemberProperties.singleOrNull {
                it.javaField?.isAccessible = true
                it.name == trashProp.name
            }

            if(taskProp != null){
                trashProp.javaField?.set(trash, taskProp.javaField?.get(task))
            }
        }

        myDao.create(trash)
    }
    private suspend fun deleteTaskAsync(task: Task):Deferred<Unit> = async(parent = this.parentJob) {
        myDao.delete(task)
    }
}