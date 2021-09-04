package com.klekchyan.nextgif.ui

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.klekchyan.nextgif.R
import timber.log.Timber

@BindingAdapter("setGif")
fun ImageView.setGif(url: String?){
    Timber.d("ImageView.setGif was called")
    url?.let {
        Timber.d("url - $url")
        val gifUri = url.toUri().buildUpon().scheme("http").build()

        GlideApp
            .with(context)
            .asGif()
            .load(gifUri)
            .optionalCenterCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_disconnected)
            .into(this)
    }
}