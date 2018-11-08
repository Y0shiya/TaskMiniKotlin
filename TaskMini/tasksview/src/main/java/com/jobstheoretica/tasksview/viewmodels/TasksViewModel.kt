package com.jobstheoretica.tasksview.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.command.impls.Command
import com.example.command.interfaces.ICommand
import com.jobstheoretica.entity.bindable.Messenger
import com.jobstheoretica.entity.bindable.Task
import com.jobstheoretica.model.dependencies.Dependency
import com.jobstheoretica.model.interfaces.ITasksModel
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import java.util.concurrent.TimeUnit
import kotlin.Exception
import kotlin.coroutines.experimental.CoroutineContext

internal class TasksViewModel:ViewModel() {

    private val myModel = Dependency(ITasksModel::class).inject()

    private val _taskLiveData = MutableLiveData<Task>()
    val taskLiveData:LiveData<Task>
        get(){
            return this.myModel.taskLiveData
        }

    val tasksLiveData:LiveData<List<Task>>
        get(){
            return this.myModel.tasksLiveData
        }

    private val _removeTaskLiveData = MutableLiveData<String>()
    val removeTaskLiveData:LiveData<String>
        get() = this._removeTaskLiveData

    val readTasksCommand:ICommand<String?> =
            Command(execute = {condition -> this.read(condition)})

    val removeTaskCommand:ICommand<String> =
            Command(execute = {id -> this.remove(id)})

    private val _debugMsgLiveData = MutableLiveData<String>()
    val debugViewModelMsgLiveData:LiveData<String>
        get() = this._debugMsgLiveData

    val debugModelMsgLiveData:LiveData<String>
        get() = this.myModel.debugModelMsgLiveData

    val messengerLiveData:LiveData<Messenger>
        get() = this.myModel.messengerLiveData

    private fun read(condition: String?){
        launch(context = UI, parent = this.myModel.parentJob) {
            myModel.readAsync(condition).await()
        }
    }

    private fun remove(id:String){
        _removeTaskLiveData.value = id
        launch (context = UI, parent = this.myModel.parentJob) {
            myModel.removeAsync(id).await()
        }
    }

    protected override fun onCleared() {
        super.onCleared()
        this.myModel.parentJob.cancel()
    }
}