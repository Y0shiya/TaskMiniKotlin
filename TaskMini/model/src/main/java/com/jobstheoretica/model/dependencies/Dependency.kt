package com.jobstheoretica.model.dependencies

import com.jobstheoretica.di.bases.DependencyBase
import com.jobstheoretica.model.impls.TaskWriterModel
import com.jobstheoretica.model.impls.TasksModel
import com.jobstheoretica.model.impls.TrashModel
import kotlin.reflect.KClass

class  Dependency <IModel:Any>(kClass: KClass<IModel>) :DependencyBase<IModel>(kClass) {

    private val implsOfModel = listOf(TaskWriterModel::class
            , TasksModel::class
            , TrashModel::class)

    override fun subInject(kClass: KClass<IModel>): IModel {

        val model = this.implsOfModel.single {
            it.java.interfaces.single().kotlin.qualifiedName.equals(kClass.qualifiedName)
        }.java.newInstance() as IModel

        return model
    }
}