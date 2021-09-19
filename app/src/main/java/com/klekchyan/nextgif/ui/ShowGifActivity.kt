package com.klekchyan.nextgif.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.klekchyan.nextgif.R
import com.klekchyan.nextgif.appComponent
import com.klekchyan.nextgif.data.LoadingState
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
            when(position){
                0 -> changeBackButtonBehavior(false)
                else -> changeBackButtonBehavior(true)
            }
        })

        viewModel.loadingState.observe(this, { loadingState ->
            when(loadingState){
                LoadingState.LOADING -> { Timber.d("LOADING") }
                LoadingState.SUCCESSFULLY -> showContent()
                else -> showDisconnectedContent()
            }
        })

        viewModel.shareButtonState.observe(this, { isClicked ->
            if(isClicked) {
                viewModel.onSharingDone()

                val url = viewModel.currentGif.value?.gifURL ?: "Content doesn't exist"
                val description = viewModel.currentGif.value?.description ?: "Content doesn't exist"

                val shareIntent = ShareCompat.IntentBuilder(this)
                    .setType("text/plain")
                    .setText("$description\n$url")
                    .intent

                if(shareIntent.resolveActivity(packageManager) != null){
                    startActivity(shareIntent)
                }
            }
        })
    }

    private fun changeNextButtonBehavior(isClickable: Boolean){
        if(isClickable){
            binding.nextButton.isClickable = isClickable
            binding.nextButton.setImageResource(R.drawable.ic_next_button)
        } else {
            binding.nextButton.isClickable = isClickable
            binding.nextButton.setImageResource(R.drawable.ic_next_button_not_clickable)
        }
    }
    private fun changeBackButtonBehavior(isClickable: Boolean){
        if(isClickable){
            binding.backButton.isClickable = isClickable
            binding.backButton.setImageResource(R.drawable.ic_back_button)
        } else {
            binding.backButton.isClickable = isClickable
            binding.backButton.setImageResource(R.drawable.ic_back_button_not_clickable)
        }
    }

    private fun showDisconnectedContent(){
        binding.gifsCard.visibility = View.GONE
        binding.disconnectedView.isVisible = true

        changeNextButtonBehavior(false)
    }

    private fun showContent(){
        binding.gifsCard.isVisible = true
        binding.disconnectedView.visibility = View.GONE

        changeNextButtonBehavior(true)
    }
}