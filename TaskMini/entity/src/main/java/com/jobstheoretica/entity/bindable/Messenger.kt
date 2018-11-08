package com.jobstheoretica.entity.bindable

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.jobstheoretica.entity.BR

class Messenger(message:String = ""):BaseObservable() {

    var message = message
        @Bindable get() = field
        @Bindable set(value) {
            field = value
            notifyPropertyChanged(BR.message)
        }
}