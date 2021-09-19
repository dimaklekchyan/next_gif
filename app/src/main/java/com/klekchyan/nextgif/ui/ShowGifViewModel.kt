package com.klekchyan.nextgif.ui

import androidx.lifecycle.*
import com.klekchyan.nextgif.data.GifRepository
import com.klekchyan.nextgif.data.LoadingState
import com.klekchyan.nextgif.models.GifDomain
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShowGifViewModel(private val gifRepository: GifRepository): ViewModel() {

    val currentPosition: LiveData<Int> = gifRepository.currentPosition
    val currentGif: LiveData<GifDomain> = gifRepository.currentGif
    val loadingState: LiveData<LoadingState> = gifRepository.loadingState

    private val _shareButtonState = MutableLiveData(false)
    val shareButtonState: LiveData<Boolean>
        get() = _shareButtonState

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

    fun onShareButtonClicked(){
        _shareButtonState.value = true
    }

    fun onSharingDone(){
        _shareButtonState.value = false
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