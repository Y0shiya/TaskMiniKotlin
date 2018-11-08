package com.example.utility.impls

import android.app.Activity
import android.support.v4.app.FragmentActivity
import com.example.utility.interfaces.IActivityReflector
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class ActivityReflector <T:Any> (fragmentActivity: FragmentActivity):IActivityReflector<T> {

    private val fragmentActivity = fragmentActivity

    @Suppress("UNCHECKED_CAST")
    override fun getDeclaredField(name: String): T {
        val activityField = this.fragmentActivity::class.memberProperties.single {
            it.isAccessible = true
            it.name == name
        }.getter.call(this.fragmentActivity) as T

        return activityField
    }
}