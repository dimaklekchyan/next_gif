package com.klekchyan.nextgif.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.klekchyan.nextgif.data.GifRepository
import com.klekchyan.nextgif.data.LoadingState
import com.klekchyan.nextgif.models.GifDomain
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShowGifViewModel(private val gifRepository: GifRepository): ViewModel() {

    val currentPosition: LiveData<Int> = gifRepository.currentPosition
    val currentGif: LiveData<GifDomain> = gifRepository.currentGif
    val loadingState: LiveData<LoadingState> = gifRepository.loadingState

    init {
        viewModelScope.launch { gifRepository.initialGif() }
    }

    fun onTryAgainButtonClicked(){
        viewModelScope.launch { gifRepository.initialGif() }
    }

    fun onNextButtonClicked(){
        viewModelScope.launch { gifRepository.getNextGif() }
    }

    fun onBackButtonClicked(){
        viewModelScope.launch { gifRepository.getPreviousGif() }
    }


    class ShowGifViewModelFactory @Inject constructor(
        private val gifRepository: GifRepository
        ) : ViewModelProvider.Factory{

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ShowGifViewModel(gifRepository) as T
        }
    }

}