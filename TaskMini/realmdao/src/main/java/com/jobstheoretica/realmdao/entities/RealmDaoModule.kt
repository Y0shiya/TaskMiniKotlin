package com.jobstheoretica.realmdao.entities

import io.realm.annotations.RealmModule

@RealmModule(library = true, classes = arrayOf(Trash::class, Task::class))
class RealmDaoModule {
}