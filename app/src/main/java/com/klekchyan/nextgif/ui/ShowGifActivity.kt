package com.klekchyan.nextgif.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.klekchyan.nextgif.R
import com.klekchyan.nextgif.appComponent
import com.klekchyan.nextgif.data.GifRepository
import timber.log.Timber
import javax.inject.Inject

class ShowGifActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ShowGifViewModel.ShowGifViewModelFactory
    private val viewModel: ShowGifViewModel by viewModels{
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.listOfGifs.observe(this, {
            Timber.d("$it")
        })
    }
}