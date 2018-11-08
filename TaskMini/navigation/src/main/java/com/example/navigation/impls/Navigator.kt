package com.example.navigation.impls

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import com.example.navigation.interfaces.INavigator
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

internal class Navigator(navController: NavController, kvOfViews:Map<String, Int>):INavigator {

    private val kvOfViews:Map<String, Int> = kvOfViews
    private val navController:NavController = navController

    override fun back() {
        this.navController.popBackStack()
    }

    override fun forward(keyOfView: String) {
        this.navController.navigate(this.kvOfViews.getValue(keyOfView))
    }

    override fun forward(keyOfView: String, bundle: Bundle?) {
        this.navController.navigate(this.kvOfViews.getValue(keyOfView), bundle)
    }
}