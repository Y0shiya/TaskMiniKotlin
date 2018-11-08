package com.jobstheoretica.restdao.interfaces

import kotlin.reflect.KClass

interface IDao {

    fun <E:Any> create(entity:E)

    fun <E:Any> read(entityKClass: KClass<E>):List<E>

    fun <E:Any> read(id:String, entityKClass: KClass<E>):E?

    fun <E:Any> update(entity:E)

    fun <E:Any> delete(entityKClass:KClass<E>)

    fun <E:Any> delete(entity:E)
}