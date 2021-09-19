package com.klekchyan.nextgif.network

import com.klekchyan.nextgif.models.GifNetworkModel
import com.klekchyan.nextgif.models.GifsContainer
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://developerslife.ru"

interface ApiService{

    @GET("random?json=true")
    suspend fun randomGIF(
    ) : GifNetworkModel

    @GET("{section}/{page}?json=true")
    suspend fun gifsFromSection(
        @Path("section") section: String,
        @Path("page") page: Int,
    ) : GifsContainer

}

