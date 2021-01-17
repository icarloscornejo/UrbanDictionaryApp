package com.prototype.data.api

import com.prototype.data.api.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UrbanDictionaryApi {

    @GET("define")
    suspend fun search(
        @Query("term") term: String
    ): SearchResponse
}