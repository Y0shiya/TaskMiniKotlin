package com.jobstheoretica.model.impls

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.jobstheoretica.entity.bindable.Task
import com.jobstheoretica.model.interfaces.ITaskWriterModel
//import com.jobstheoretica.realmdao.interfaces.IDao
import com.jobstheoretica.restdao.interfaces.IDao
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import java.util.*

internal class TaskWriterModel:ITaskWriterModel {

    private val myDao = com.jobstheoretica.restdao.dependencies.Dependency(IDao::class).inject()

    override val parentJob: Job = Job()

    private var _isNew = true

    private val _taskLiveData = MutableLiveData<Task>()
    override val taskLiveData: LiveData<Task>
        get() = this._taskLiveData

    override suspend fun readTaskAsync(id: String?): Deferred<Unit> = async(parent = this.parentJob) {
        if(id != null){
            _isNew = false

            //既存タスク 取得
            val task = myDao.read(id, Task::class)
            if(task != null){
                _taskLiveData.postValue(task)
            }
        }
        else{
            _isNew = true

            //新規タスク 生成
            val dummyId = UUID.randomUUID().toString()
            val newTask = Task(dummyId, "")
            _taskLiveData.postValue(newTask)
        }
    }

    override suspend fun createTaskAsync(task: Task): Deferred<Unit> = async(parent = this.parentJob) {
        when(_isNew){
            true -> myDao.create(task)
            else -> myDao.update(task)
        }
    }
}