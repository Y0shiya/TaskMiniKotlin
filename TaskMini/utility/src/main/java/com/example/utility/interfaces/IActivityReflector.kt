package com.example.utility.interfaces

interface IActivityReflector <T:Any> {

    fun getDeclaredField(name: String):T
}