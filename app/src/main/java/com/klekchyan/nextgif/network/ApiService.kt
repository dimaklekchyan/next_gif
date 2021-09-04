package com.klekchyan.nextgif.network

import com.klekchyan.nextgif.models.GifNetworkModel
import com.klekchyan.nextgif.models.GifsContainer
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://developerslife.ru"// /latest/0?json=true"

private const val JSON_QUERY = "json"
private const val JSON_QUERY_VALUE = true

interface ApiService{

    @GET("random")
    suspend fun randomGIF(
        @Query(JSON_QUERY) json: Boolean = JSON_QUERY_VALUE
    ) : GifNetworkModel

    @GET("{section}/{page}")
    suspend fun gifsFromSection(
        @Path("section") section: String,
        @Path("page") page: Int,
        @Query(JSON_QUERY) json: Boolean = JSON_QUERY_VALUE
    ) : GifsContainer

}

