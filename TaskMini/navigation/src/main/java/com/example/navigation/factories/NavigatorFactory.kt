package com.example.navigation.factories

import android.app.Activity
import androidx.navigation.NavController
import com.example.navigation.impls.Navigator
import com.example.navigation.interfaces.INavigator
import com.example.navigation.interfaces.INavigatorFactory

class NavigatorFactory(navController: NavController, kvOfViews:Map<String, Int>):INavigatorFactory {

    private val navController = navController
    private val kvOfViews = kvOfViews

    override fun create():INavigator{
        return Navigator(this.navController, this.kvOfViews)
    }
}