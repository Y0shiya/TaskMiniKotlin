package com.jobstheoretica.restdao.impls

import android.app.Notification
import android.util.Log
import java.net.SocketTimeoutException;
import com.google.gson.GsonBuilder
import com.jobstheoretica.entity.bindable.Task
import com.jobstheoretica.entity.bindable.Trash
import com.jobstheoretica.restdao.interfaces.IDao
import com.jobstheoretica.restdao.interfaces.ITaskApiService
import com.jobstheoretica.restdao.interfaces.ITrashApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

internal class RestDao:IDao {

    //private val baseUrl:String = "http://10.0.2.2/TaskMiniBackend/"
    private val baseUrl:String = "http://10.0.2.2:49751/"
    private val logger:HttpLoggingInterceptor
    private val httpClient:OkHttpClient
    private val retrofit:Retrofit

    private val OK = 200
    private val CREATED = 201
    private val NO_CONTENT = 204
    private val BAD_REQUEST = 400
    private val INTERNAL_SERVERT_ERROR = 500

    private val TAG_DAO_ERROR = "DaoError"
    private val TAG_HTTP_LOGGER = "HttpLogger"

    init {
        this.logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            if(it != null){
                Log.d(TAG_HTTP_LOGGER, it)
            }
        })
        this.logger.level = HttpLoggingInterceptor.Level.BODY

        this.httpClient = OkHttpClient()
                .newBuilder()
                .addInterceptor(this.logger)
                .build()

        this.retrofit = Retrofit
                .Builder()
                .baseUrl(this.baseUrl)
                .client(this.httpClient)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
    }

    override fun <E : Any> create(entity: E) {
        var call:Call<E>? = null

        when(entity){
            entity is Task ->{
                call = this.retrofit.create(ITaskApiService::class.java).psot(entity as Task) as Call<E>?
            }
            entity is Trash ->{
                call = this.retrofit.create(ITrashApiService::class.java).post(entity as Trash) as Call<E>?
            }
        }

        if(entity is Task){
            call = this.retrofit.create(ITaskApiService::class.java).psot(entity as Task) as Call<E>?
        }
        else if(entity is Trash){
            call = this.retrofit.create(ITrashApiService::class.java).post(entity as Trash) as Call<E>?
        }

        if(call != null){
            try{
                val response = call.execute()
                if(response.code() == this.CREATED){
                }
            }catch (ex:SocketTimeoutException){
                Log.d(this.TAG_DAO_ERROR, ex.message)
                Log.d(this.TAG_DAO_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
            }catch (ex:Exception){
                Log.d(this.TAG_DAO_ERROR, ex.message)
                Log.d(this.TAG_DAO_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
            }
        }
    }

    override fun <E : Any> read(entityKClass: KClass<E>): List<E> {

        var call:Call<List<E>>? = null

        when(entityKClass){
            Task::class -> {
                call = this.retrofit.create(ITaskApiService::class.java).get() as Call<List<E>>
            }
            Trash::class ->{
                call = this.retrofit.create(ITrashApiService::class.java).get() as Call<List<E>>
            }
        }

        var entities = mutableListOf<E>()

        if(call != null){
            try{
                val response = call.execute()
                if((response.code() == this.OK)
                        && (response.body() != null)){
                    entities.addAll(response.body()!!)
                }
            }catch (ex:SocketTimeoutException){
                Log.d(this.TAG_DAO_ERROR, ex.message)
                Log.d(this.TAG_DAO_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
            }catch (ex:Exception){
                Log.d(this.TAG_DAO_ERROR, ex.message)
                Log.d(this.TAG_DAO_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
            }

        }

        return entities
    }

    override fun <E : Any> read(id: String, entityKClass: KClass<E>): E? {
        var call:Call<E>? = null

        when(entityKClass){
            Task::class ->{
                call = this.retrofit.create(ITaskApiService::class.java).get(id) as Call<E>?
            }
        }

        if(call != null){
            try{
                val response = call.execute()

                if((response.code() == this.OK)
                        && (response.body() != null)){
                    return response.body()!!
                }
            }catch (ex:SocketTimeoutException){
                Log.d(this.TAG_DAO_ERROR, ex.message)
                Log.d(this.TAG_DAO_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
            }
            catch (ex:Exception){
                Log.d(this.TAG_DAO_ERROR, ex.message)
                Log.d(this.TAG_DAO_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
            }
        }

        return null
    }

    override fun <E : Any> update(entity: E) {
        var call:Call<E>? = null

        if(entity is Task){
            call = this.retrofit.create(ITaskApiService::class.java).put(entity as Task) as Call<E>?
        }

        if(call != null){
            try{
                val response = call.execute()
                if(response.code() == this.NO_CONTENT){

                }
            }
            catch (ex:SocketTimeoutException){
                Log.d(this.TAG_DAO_ERROR, ex.message)
                Log.d(this.TAG_DAO_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
            }catch (ex:Exception){
                Log.d(this.TAG_DAO_ERROR, ex.message)
                Log.d(this.TAG_DAO_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
            }
        }
    }

    override fun <E : Any> delete(entityKClass: KClass<E>) {
        var call:Call<List<E>>? = null

        when(entityKClass){
            Trash::class ->{
                call = this.retrofit.create(ITrashApiService::class.java).delete() as Call<List<E>>
            }
        }

        if(call != null){
            try{
                val response = call.execute()
                if(response.code() == this.NO_CONTENT){

                }
            }catch (ex:SocketTimeoutException){
                Log.d(this.TAG_DAO_ERROR, ex.message)
                Log.d(this.TAG_DAO_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
            }catch (ex:Exception){
                Log.d(this.TAG_DAO_ERROR, ex.message)
                Log.d(this.TAG_DAO_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
            }
        }
    }

    override fun <E : Any> delete(entity: E) {
        var call:Call<E>? = null

        val id = entity::class.declaredMemberProperties.single {
            it.javaField?.isAccessible = true
            it.name == "id"
        }.javaField?.get(entity) as String

        if(id.isNullOrEmpty())
            return

        if(entity is Task){
            call = this.retrofit.create(ITaskApiService::class.java).delete(id) as Call<E>?
        }
        else if(entity is Trash){
            call = this.retrofit.create(ITrashApiService::class.java).delete(id) as Call<E>?
        }

        if(call != null){
            try{
                val response = call.execute()
                if(response.code() == this.NO_CONTENT){

                }
            }catch (ex:SocketTimeoutException){
                Log.d(this.TAG_DAO_ERROR, ex.message)
                Log.d(this.TAG_DAO_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
            }
            catch (ex:Exception){
                Log.d(this.TAG_DAO_ERROR, ex.message)
                Log.d(this.TAG_DAO_ERROR, ex.stackTrace.map { it -> "\n at " + it.toString() }.toString())
            }
        }
    }
}