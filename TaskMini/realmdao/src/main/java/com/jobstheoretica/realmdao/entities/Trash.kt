package com.jobstheoretica.realmdao.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class Trash:RealmObject() {

    @PrimaryKey
    var id:String = ""

    @Required
    var note:String = ""

    var order:Int = 0
}