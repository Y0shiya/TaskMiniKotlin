package com.example.behavior.interfaces

interface IBehavior {

    fun behave():IBehavior

    fun updateViewState():IBehavior

    fun eventToCommand():IBehavior
}