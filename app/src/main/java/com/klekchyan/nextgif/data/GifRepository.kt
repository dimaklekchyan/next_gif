package com.klekchyan.nextgif.data

import com.klekchyan.nextgif.models.GifDomain
import com.klekchyan.nextgif.models.asGifDomain
import com.klekchyan.nextgif.models.asListOfGifDomain
import com.klekchyan.nextgif.network.ApiService
import javax.inject.Inject

interface GifRepository {
    suspend fun getRandomGif() : GifDomain
    suspend fun getGifsFromSection(section: String, page: Int) : List<GifDomain>
}

class GifRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : GifRepository {

    override suspend fun getRandomGif(): GifDomain {
        return apiService.randomGIF().asGifDomain()
    }

    override suspend fun getGifsFromSection(section: String, page: Int): List<GifDomain> {
        return apiService.gifsFromSection(section, page).asListOfGifDomain()
    }
}