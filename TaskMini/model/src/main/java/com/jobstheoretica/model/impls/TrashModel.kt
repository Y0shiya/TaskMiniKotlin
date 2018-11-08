package com.jobstheoretica.model.impls

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.jobstheoretica.entity.bindable.Task
import com.jobstheoretica.entity.bindable.Trash
import com.jobstheoretica.model.interfaces.ITrashModel
//import com.jobstheoretica.realmdao.dependencies.Dependency
//import com.jobstheoretica.realmdao.interfaces.IDao
import com.jobstheoretica.restdao.dependencies.Dependency
import com.jobstheoretica.restdao.interfaces.IDao
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import javax.xml.transform.sax.TransformerHandler
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

internal class TrashModel:ITrashModel {

    private val myDao = Dependency(IDao::class).inject()

    override val parentJob: Job = Job()

    private val _trashLiveData = MutableLiveData<List<Trash>>()
    override val trashLiveData: LiveData<List<Trash>>
        get() = this._trashLiveData

    override suspend fun readTrashAsync(): Deferred<Unit> = async(parent = this.parentJob) {

        val trash = myDao.read(Trash::class)
        if(trash != null){
            _trashLiveData.postValue(trash)
        }
    }

    override suspend fun revertTrashAsync(trash: Trash): Deferred<Unit> = async(parent = this.parentJob) {
        createTaskAsync(trash).await()
        deleteTrashAsync(trash).await()
    }
    private suspend fun createTaskAsync(trash: Trash):Deferred<Unit> = async(parent = this.parentJob) {
        var task = Task()

        for(taskProp in task::class.declaredMemberProperties){
            taskProp.javaField?.isAccessible = true

            val trashProp = trash::class.declaredMemberProperties.singleOrNull {
                it.javaField?.isAccessible = true
                it.name == taskProp.name
            }

            if(trashProp != null){
                taskProp.javaField?.set(task, trashProp.javaField?.get(trash))
            }
        }

        myDao.create(task)
    }

    override suspend fun deleteTrashAsync(trash: Trash?): Deferred<Unit> = async(parent = this.parentJob) {
        if(trash != null){
            myDao.delete(trash)
        }
        else{
            myDao.delete(Trash::class)
        }
    }
}