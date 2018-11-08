package com.jobstheoretica.model.interfaces

import android.arch.lifecycle.LiveData
import com.jobstheoretica.entity.bindable.Task
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job

interface ITaskWriterModel {

    val parentJob:Job

    val taskLiveData:LiveData<Task>

    suspend fun readTaskAsync(id:String?):Deferred<Unit>

    suspend fun createTaskAsync(task: Task):Deferred<Unit>
}