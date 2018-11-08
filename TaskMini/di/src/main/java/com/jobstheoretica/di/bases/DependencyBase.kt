package com.jobstheoretica.di.bases

import com.jobstheoretica.di.interfaces.IDependency
import kotlin.reflect.KClass

abstract class DependencyBase <T:Any>(kClass: KClass<T>):IDependency <T> {

    private val kClass = kClass

    override fun inject(): T {
        return this.subInject(this.kClass)
    }

    protected open fun subInject(kClass: KClass<T>):T = throw NotImplementedError()

}