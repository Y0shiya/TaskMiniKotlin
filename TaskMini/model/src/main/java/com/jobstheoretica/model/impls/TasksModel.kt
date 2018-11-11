package com.jobstheoretica.model.impls

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
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

    private val TAG_MODEL_ERROR = "ModelError"

    private val myDao = Dependency(IDao::class).inject()

    private var _parentJob:Job = Job()
    override val parentJob: Job
        get() {
            this._parentJob = Job()
            return this._parentJob
        }

    private val _taskLiveData = MutableLiveData<Task>()
    override val taskLiveData: LiveData<Task>
        get() = this._taskLiveData

    private val _tasksLiveData = MutableLiveData<List<Task>>()
    override val tasksLiveData: LiveData<List<Task>>
        get() = this._tasksLiveData

    private val _messengerLiveData = MutableLiveData<Messenger>()
    override val messengerLiveData: LiveData<Messenger>
        get() = this._messengerLiveData

    override fun canCancelJob(): Boolean {
        return !this._parentJob.isCompleted
    }

    override fun cancelJob() {
        this._parentJob.cancel()
    }

    override suspend fun readTasksAsync(condition: String?): Deferred<Unit> = async(parent = this._parentJob) {
        try{

            val tasks = myDao.read(Task::class)
            _tasksLiveData.postValue(tasks)

        }catch (ex:CancellationException){
            Log.d(TAG_MODEL_ERROR, ex.message)
            Log.d(TAG_MODEL_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
        }

        return@async
    }

    override suspend fun removeTaskAsync(id: String): Deferred<Unit> = async(parent = this._parentJob) {
        try{

            val task = myDao.read(Task::class).singleOrNull{
                it.id == id
            }

            if(task != null){
                createTrashAsync(task).await()
                deleteTaskAsync(task).await()
            }

        }catch (ex:CancellationException){
            Log.d(TAG_MODEL_ERROR, "removeTaskAsync")
            Log.d(TAG_MODEL_ERROR, ex.message)
            Log.d(TAG_MODEL_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
        }

        return@async
    }
    private suspend fun createTrashAsync(task: Task):Deferred<Unit> = async(parent = this._parentJob) {

        try {

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

        }catch (ex:CancellationException){
            Log.d(TAG_MODEL_ERROR, "createTrashAsync")
            Log.d(TAG_MODEL_ERROR, ex.message)
            Log.d(TAG_MODEL_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
        }

        return@async
    }
    private suspend fun deleteTaskAsync(task: Task):Deferred<Unit> = async(parent = this._parentJob) {

        try{

            myDao.delete(task)

        }catch (ex:CancellationException){
            Log.d(TAG_MODEL_ERROR, "deleteTaskAsync")
            Log.d(TAG_MODEL_ERROR, ex.message)
            Log.d(TAG_MODEL_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
        }

        return@async
    }
}