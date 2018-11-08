package com.jobstheoretica.trashview.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.command.impls.Command
import com.example.command.interfaces.ICommand
import com.jobstheoretica.entity.bindable.Trash
import com.jobstheoretica.model.dependencies.Dependency
import com.jobstheoretica.model.interfaces.ITrashModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

internal class TrashViewModel:ViewModel() {

    private val myModel = Dependency(ITrashModel::class).inject()

    val trashLiveData:LiveData<List<Trash>>
        get() = this.myModel.trashLiveData

    val readTrashCommand:ICommand<Unit?> = Command(execute = {_ -> this.readTrash()})

    val revertTrashCommand:ICommand<Trash> = Command(execute = {trash -> this.revertTrash(trash)})

    val deleteCommand:ICommand<Trash?> = Command(execute = {trash -> this.deleteTrash(trash)})

    private fun readTrash(){
        launch(context = UI, parent = this.myModel.parentJob) {
            myModel.readTrashAsync().await()
        }
    }

    private fun revertTrash(trash: Trash){
        launch(context = UI, parent = this.myModel.parentJob) {
            myModel.revertTrashAsync(trash)
        }
    }

    private fun deleteTrash(trash: Trash?){
        launch(context = UI, parent = this.myModel.parentJob) {
            myModel.deleteTrashAsync(trash).await()
        }
    }
}