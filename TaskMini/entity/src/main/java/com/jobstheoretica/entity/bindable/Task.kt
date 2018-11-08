package com.jobstheoretica.entity.bindable

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.jobstheoretica.entity.BR

class Task(id:String = "", note:String = "", order:Int = 0):BaseObservable() {

    var id = id
        @Bindable get() = field
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.id)
        }

    var note = note
        @Bindable get() = field
        @Bindable set(value){
            field = value
            notifyPropertyChanged(BR.note)
        }

    var order = order
        @Bindable get() = field
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.order)
        }
}