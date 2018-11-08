package com.example.navigation.factories

import android.app.Activity
import android.support.v4.app.Fragment
import androidx.navigation.NavController
import com.example.navigation.impls.Navigator
import com.example.navigation.interfaces.INavigator
import com.example.navigation.interfaces.INavigatorFactory
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class SharedNavigatorFactory(fragment: Fragment):INavigatorFactory {

    private val fragment = fragment

    private var _sharedNavigator:INavigator? = null
    private val sharedNavigator:INavigator?
        get() {
            if(this._sharedNavigator == null){

                val entryPointActivity = this.fragment.activity as Activity

                this._sharedNavigator = entryPointActivity::class.memberProperties.single {
                    it.isAccessible = true
                    it.name == "navigator"
                }.getter.call(entryPointActivity) as INavigator
            }

            return this._sharedNavigator
        }

    override fun create():INavigator{
        if(this.sharedNavigator != null){
            return this.sharedNavigator as INavigator
        }
        else{
            throw Exception("SharedNavigator is null.")
        }
    }
}