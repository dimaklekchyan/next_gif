package com.klekchyan.nextgif.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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

        viewModel.currentPosition.observe(this, { position ->
            binding.backButton.isClickable = when(position){
                0 -> false
                else -> true
            }
        })

        viewModel.loadingState.observe(this, { loadingState ->
            when(loadingState){
                LoadingState.LOADING -> { Timber.d("LOADING") }
                LoadingState.SUCCESSFULLY -> showContent()
                else -> showDisconnectedContent()
            }
        })
    }

    private fun showDisconnectedContent(){
        binding.gifsCard.visibility = View.GONE
        binding.disconnectedImage.isVisible = true
        binding.tryAgain.isVisible = true

        binding.nextButton.isClickable = false
    }

    private fun showContent(){
        binding.gifsCard.isVisible = true
        binding.disconnectedImage.visibility = View.GONE
        binding.tryAgain.visibility = View.GONE

        binding.nextButton.isClickable = true
    }
}