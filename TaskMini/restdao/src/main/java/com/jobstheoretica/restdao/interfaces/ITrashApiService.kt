package com.jobstheoretica.restdao.interfaces

import com.jobstheoretica.entity.bindable.Trash
import retrofit2.Call
import retrofit2.http.*

internal interface ITrashApiService {

    @GET("api/trash")
    fun get():Call<List<Trash>>

    @POST("api/trash")
    fun post(@Body trash: Trash):Call<Trash>

    @DELETE("api/trash")
    fun delete():Call<List<Trash>>

    @DELETE("api/trash/{id}")
    fun delete(@Path("id") id:String):Call<Trash>
}