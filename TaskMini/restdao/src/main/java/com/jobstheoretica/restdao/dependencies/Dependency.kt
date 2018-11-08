package com.jobstheoretica.restdao.dependencies

import com.jobstheoretica.di.bases.DependencyBase
import com.jobstheoretica.restdao.impls.RestDao
import com.jobstheoretica.restdao.interfaces.IDao
import kotlin.reflect.KClass

class Dependency<T:IDao>(kClass:KClass<T>):DependencyBase<T>(kClass) {

    override fun inject(): T {
        return RestDao() as T
    }
}