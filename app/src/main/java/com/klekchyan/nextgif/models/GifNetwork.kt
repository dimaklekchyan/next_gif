package com.klekchyan.nextgif.models

data class GifNetworkModel(
    var id: Long?,
    var description: String?,
    var gifURL: String?,
    var previewURL: String?,
    var width: String?,
    var height: String?
)

data class GifsContainer(
    var result: List<GifNetworkModel>
)

fun GifNetworkModel.asGifDomain(): GifDomain{
    return GifDomain(
        id = this.id,
        description = this.description,
        gifURL = this.gifURL,
        previewURL = this.previewURL,
        width = this.width,
        height = this.height
    )
}

fun GifsContainer.asListOfGifDomain(): List<GifDomain>{
    return this.result.map {
        GifDomain(
            id = it.id,
            description = it.description,
            gifURL = it.gifURL,
            previewURL = it.previewURL,
            width = it.width,
            height = it.height
        )
    }
}