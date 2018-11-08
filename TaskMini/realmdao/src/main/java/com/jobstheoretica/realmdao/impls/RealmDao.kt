package com.jobstheoretica.realmdao.impls


import com.jobstheoretica.realmdao.entities.RealmDaoModule
import com.jobstheoretica.realmdao.entities.Task
import com.jobstheoretica.realmdao.entities.Trash
import com.jobstheoretica.realmdao.interfaces.IDao
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

internal class RealmDao:IDao {

    private val realmObjKClasses = listOf(Trash::class, Task::class)

    private val realm:Realm
        get() {
            val config = RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .name("taskmin.realm")
                    .modules(RealmDaoModule())
                    .schemaVersion(0)
                    .build()

            return Realm.getInstance(config)
        }

    override fun <E : Any> create(entity: E) {

        val realmInstance = this.realm

        val realmObjKClass = this.realmObjKClasses.single {
            it.java.simpleName == entity::class.java.simpleName
        }

        realmInstance.executeTransaction {
            val realmObj =  it.createObject(realmObjKClass.java, UUID.randomUUID().toString())

            for (entityProp in entity::class.declaredMemberProperties){
                entityProp.javaField?.isAccessible = true
                if(entityProp.name == "id"){
                    continue
                }

                val realmObjSetter = realmObj::class.java.methods.singleOrNull {
                    val setterName = "set" + entityProp.name.substring(0..0).toUpperCase() + entityProp.name.substring(1, entityProp.name.count() - 1)
                    it.name.contains(setterName)
                }

                if(realmObjSetter != null){
                    realmObjSetter.invoke(realmObj, entityProp.javaField?.get(entity))
                }
            }
        }
    }

    override fun <E : Any> read(entityKClass: KClass<E>): List<E> {

        val entities = mutableListOf<E>()

        val realmObjKClass = this.realmObjKClasses.single {
            it.java.simpleName == entityKClass.java.simpleName
        }

        val realmInstance = this.realm

        for(realmObj in realmInstance.where(realmObjKClass.java).findAll()){

            val entity = entityKClass.java.newInstance()

            for(entityProp in entity::class.declaredMemberProperties){
                entityProp.javaField?.isAccessible = true

                val realmObjGetter = realmObj::class.java.methods.singleOrNull{
                    val getterName = "get" + entityProp.name.substring(0..0).toUpperCase() + entityProp.name.substring(1..entityProp.name.count() - 1)
                    it.name.contains(getterName)
                }

                if(realmObjGetter != null){
                    entityProp.javaField?.set(entity, realmObjGetter.invoke(realmObj))
                }
            }

            entities.add(entity)
        }

        return entities
    }

    override fun <E : Any> update(entity: E) {

        val realmInstance = this.realm

        val realmObjKClass = this.realmObjKClasses.single {
            it.java.simpleName == entity::class.java.simpleName
        }

        realmInstance.executeTransaction {
            val realmObj = realmInstance.where(realmObjKClass.java).findAll().single {
                val realmObjGetter = it::class.java.methods.single{
                    val getterName = "getId"
                    it.name.contains(getterName)
                }

                (realmObjGetter.invoke(it) as String) == (entity::class.declaredMemberProperties.single {
                    it.javaField?.isAccessible = true
                    it.name == "id"
                }.javaField?.get(entity) as String)
            }

            for (entityProp in entity::class.declaredMemberProperties){
                entityProp.javaField?.isAccessible = true
                if(entityProp.name == "id"){
                    continue
                }

                val realmObjSetter = realmObj::class.java.methods.singleOrNull {
                    val setterName = "set" + entityProp.name.substring(0..0).toUpperCase() + entityProp.name.substring(1, entityProp.name.count() - 1)
                    it.name.contains(setterName)
                }

                if(realmObjSetter != null){
                    realmObjSetter.invoke(realmObj, entityProp.javaField?.get(entity))
                }
            }
        }

    }

    override fun <E : Any> delete(entity: E) {

        val realmObjKClass = this.realmObjKClasses.single {
            it.java.simpleName == entity::class.java.simpleName
        }

        val idProp = entity::class.declaredMemberProperties.single {
            it.name == "id" }
        idProp.javaField?.isAccessible = true

        val idValue = idProp.javaField?.get(entity) as String

        val realmInstance = this.realm

        realmInstance.executeTransaction {
            val realmObjects = realmInstance.where(realmObjKClass.java)
                    .equalTo("id", idValue)
                    .findAll()

            realmObjects.deleteAllFromRealm()
        }
    }
}