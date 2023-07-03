package com.example.pixabay

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaApi {

    @GET("api/")
    fun searchImage (
        @Query ("q") searchWord : String,
        @Query ("key") key: String = "38038176-0560b5b0ececba51d14b3e8a9",
        @Query ("per_page") perPage:Int = 3,
        @Query ("page") page: Int
        ) : Call<PixaModel>
}