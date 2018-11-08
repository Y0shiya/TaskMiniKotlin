package com.jobstheoretica.model.interfaces

import android.arch.lifecycle.LiveData
import com.jobstheoretica.entity.bindable.Trash
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job

interface ITrashModel {

    val parentJob:Job

    val trashLiveData:LiveData<List<Trash>>

    suspend fun readTrashAsync():Deferred<Unit>

    suspend fun revertTrashAsync(trash: Trash):Deferred<Unit>

    suspend fun deleteTrashAsync(trash:Trash?):Deferred<Unit>
}