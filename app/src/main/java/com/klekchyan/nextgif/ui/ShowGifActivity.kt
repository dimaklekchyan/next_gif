package com.klekchyan.nextgif.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.klekchyan.nextgif.R
import com.klekchyan.nextgif.appComponent
import com.klekchyan.nextgif.databinding.ShowGifActivityBinding
import timber.log.Timber
import javax.inject.Inject

class ShowGifActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ShowGifViewModel.ShowGifViewModelFactory
    private val viewModel: ShowGifViewModel by viewModels{
        viewModelFactory
    }
    private var _binding: ShowGifActivityBinding? = null
    private val binding: ShowGifActivityBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.show_gif_activity)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }
}