package com.jobstheoretica.restdao.interfaces

import com.jobstheoretica.entity.bindable.Task
import retrofit2.Call
import retrofit2.http.*

internal interface ITaskApiService {

    @GET("api/tasks")
    fun get(): Call<List<Task>>

    @GET("api/tasks/{id}")
    fun get(@Path("id") id: String):Call<Task>

    @POST("api/tasks")
    fun psot(@Body task: Task):Call<Task>

    @PUT("api/tasks")
    fun put(@Body task: Task):Call<Task>

    @DELETE("api/tasks/{id}")
    fun delete(@Path("id") id:String):Call<Task>
}