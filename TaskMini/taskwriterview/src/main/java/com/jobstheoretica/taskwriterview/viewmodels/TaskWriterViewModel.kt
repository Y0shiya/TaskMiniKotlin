package com.jobstheoretica.taskwriterview.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.command.impls.Command
import com.example.command.interfaces.ICommand
import com.jobstheoretica.entity.bindable.Task
import com.jobstheoretica.model.dependencies.Dependency
import com.jobstheoretica.model.interfaces.ITaskWriterModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

internal class TaskWriterViewModel:ViewModel() {

    private val myModel = Dependency(ITaskWriterModel::class).inject()

    val taskLiveData:LiveData<Task>
        get() = this.myModel.taskLiveData

    private val _notSaveLiveData = MutableLiveData<String>()
    val notSaveLiveData:LiveData<String>
        get() = this._notSaveLiveData

    val readTaskCommand:ICommand<String?> = Command(execute = {id -> this.readTask(id) });

    val saveTaskCommand:ICommand<Task> = Command(execute = {task -> this.saveTask(task)}, canExecute = {task -> this.canSaveTask(task)})

    private fun readTask(id:String?){

        launch (context = UI, parent = this.myModel.parentJob){
            myModel.readTaskAsync(id).await()
        }
    }

    private fun canSaveTask(task: Task):Boolean{

        if(task.note.isNullOrEmpty()){
            this._notSaveLiveData.value = "Task or Note..."
            return false
        }

        return true
    }
    private fun saveTask(task: Task){
        launch (context = UI, parent = this.myModel.parentJob){
            myModel.createTaskAsync(task).await()
        }
    }
}