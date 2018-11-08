package com.example.command.impls

import com.example.command.interfaces.ICommand

class Command <T> (execute: (T) -> Unit, canExecute: (T) -> Boolean = {_ -> true}):ICommand<T> {

    private val _canExecute = canExecute
    private val _execute = execute

    override fun canExecute(param: T): Boolean = this._canExecute(param)

    override fun execute(param: T) = this._execute(param)

    /*@Suppress("UNCHECKED_CAST")
    override fun canExecute(param: Any?): Boolean = this._canExecute(param as T)

    @Suppress("UNCHECKED_CAST")
    override fun execute(param: Any?) = this._execute(param as T)*/
}