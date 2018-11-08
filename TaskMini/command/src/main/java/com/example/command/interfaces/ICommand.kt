package com.example.command.interfaces

interface ICommand<T:Any?> {

    fun canExecute(param:T):Boolean

    fun execute(param:T)
}