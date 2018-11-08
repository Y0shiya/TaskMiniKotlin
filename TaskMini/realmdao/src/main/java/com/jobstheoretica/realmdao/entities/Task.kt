package com.jobstheoretica.realmdao.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Task:RealmObject() {

    @PrimaryKey
    var id:String = ""

    @Required
    var note:String = ""

    var order:Int = 0
}