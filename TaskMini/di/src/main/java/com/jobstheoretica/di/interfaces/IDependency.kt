package com.jobstheoretica.di.interfaces

import kotlin.reflect.KClass

interface IDependency <T:Any> {

    fun inject():T
}