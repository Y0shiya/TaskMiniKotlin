package com.jobstheoretica.realmdao.dependencies

import com.jobstheoretica.di.bases.DependencyBase
import com.jobstheoretica.realmdao.impls.RealmDao
import com.jobstheoretica.realmdao.interfaces.IDao
import kotlin.reflect.KClass

class Dependency<T:IDao>(kClass: KClass<T>):DependencyBase<T>(kClass) {

    override fun subInject(kClass: KClass<T>): T {
        return RealmDao() as T
    }
}