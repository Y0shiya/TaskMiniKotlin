package com.example.behavior.bases

import com.example.behavior.interfaces.IBehavior

open class BehaviorBase:IBehavior {

    override fun behave(): IBehavior {
        this.subBehave()
        return this
    }
    protected open fun subBehave(){
        throw NotImplementedError()
    }

    override fun updateViewState(): IBehavior {
        this.subUpdateViewState()
        return this
    }
    protected open fun subUpdateViewState(){
        throw NotImplementedError()
    }

    override fun eventToCommand(): IBehavior {
        this.subEventToCommand()
        return this
    }
    protected open fun subEventToCommand(){
        throw NotImplementedError()
    }
}