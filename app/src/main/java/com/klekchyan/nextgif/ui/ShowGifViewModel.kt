package com.klekchyan.nextgif.ui

import androidx.lifecycle.*
import com.klekchyan.nextgif.data.GifRepository
import com.klekchyan.nextgif.models.GifDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

enum class LoadingState{ LOADING, SUCCESSFULLY, FAILURE }

class ShowGifViewModel(private val gifRepository: GifRepository): ViewModel() {

    private val queue = HashMap<Int, GifDomain>()

    private val _currentPosition = MutableLiveData<Int>()
    private val _currentGif = MutableLiveData<GifDomain>()
    private val _loadingState = MutableLiveData<LoadingState>()

    val currentPosition: LiveData<Int>
        get() = _currentPosition
    val currentGif: LiveData<GifDomain>
        get() = _currentGif
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    init {
        _currentPosition.value = 0
        getNewGif(_currentPosition.value!!)
    }

    private fun getNewGif(position: Int) {
        _loadingState.value = LoadingState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val gifDomain: GifDomain = gifRepository.getRandomGif()
                queue[position] = gifDomain
                withContext(Dispatchers.Main){
                    _currentGif.value = gifDomain
                    _loadingState.value = LoadingState.SUCCESSFULLY
                }
            } catch (ex: Exception){
                withContext(Dispatchers.Main){
                    _loadingState.value = LoadingState.FAILURE
                }
                Timber.d("$ex")
            }
        }
    }

    fun onTryAgainButtonClicked(){
        getNewGif(_currentPosition.value!!)
    }

    fun onNextButtonClicked(){
        val nextPosition = _currentPosition.value!!.plus(1)
        _currentPosition.value = nextPosition
        if(queue.containsKey(nextPosition)) {
            _currentGif.value = queue[nextPosition]
            _loadingState.value = LoadingState.SUCCESSFULLY
        } else {
            getNewGif(nextPosition)
        }
    }

    fun onBackButtonClicked(){
        val previousPosition = _currentPosition.value!!.minus(1)
        _currentPosition.value = previousPosition
        if(queue.containsKey(previousPosition)) {
            _currentGif.value = queue[previousPosition]
            _loadingState.value = LoadingState.SUCCESSFULLY
        } else {
            getNewGif(previousPosition)
        }
    }

    override fun onCleared() {
        super.onCleared()
        queue.clear()
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