package com.jobstheoretica.realmdao.interfaces

import kotlin.reflect.KClass

interface IDao {

    fun <E:Any> create(entity:E)

    fun <E:Any> read(entityKClass: KClass<E>):List<E>

    fun <E:Any> update(entity:E)

    fun <E:Any> delete(entity:E)
}