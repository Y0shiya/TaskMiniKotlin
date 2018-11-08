package com.example.navigation.interfaces

import android.os.Bundle

public interface INavigator {

    fun back()

    fun forward(keyOfView:String)

    fun forward(keyOfView:String, bundle:Bundle?)
}