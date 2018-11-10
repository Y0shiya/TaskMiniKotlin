package com.jobstheoretica.model.interfaces

import android.arch.lifecycle.LiveData
import com.jobstheoretica.entity.bindable.Messenger
import com.jobstheoretica.entity.bindable.Task
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job


public interface ITasksModel {

    val parentJob:Job

    val taskLiveData:LiveData<Task>

    val tasksLiveData:LiveData<List<Task>>

    val messengerLiveData:LiveData<Messenger>

    val debugModelMsgLiveData:LiveData<String>

    suspend fun readTasksAsync(condition:String?):Deferred<Unit>

    suspend fun removeTaskAsync(id:String):Deferred<Unit>
}