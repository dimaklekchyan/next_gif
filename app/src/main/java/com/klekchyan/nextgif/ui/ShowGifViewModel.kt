package com.klekchyan.nextgif.ui

import androidx.lifecycle.*
import com.klekchyan.nextgif.data.GifRepository
import com.klekchyan.nextgif.models.GifDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ShowGifViewModel(private val gifRepository: GifRepository): ViewModel() {

    private val queue = HashMap<Int, GifDomain>()

    private val _currentPosition = MutableLiveData<Int>()
    private val _currentGif = MutableLiveData<GifDomain>()

    val currentPosition: LiveData<Int>
        get() = _currentPosition
    val currentGif: LiveData<GifDomain>
        get() = _currentGif

    init {
        _currentPosition.value = 0
        getGif(_currentPosition.value!!)
    }

    private fun getGif(position: Int) {
        if(queue.containsKey(position)) {
            _currentGif.value = queue[position]
        } else {
            viewModelScope.launch {
                try {
                    val gifDomain = gifRepository.getRandomGif()
                    queue[position] = gifDomain
                    withContext(Dispatchers.Main){ _currentGif.value = gifDomain }
                } catch (ex: Exception){
                    Timber.d("$ex")
                }
            }
        }
    }

    fun onNextButtonClicked(){
        _currentPosition.value = _currentPosition.value?.plus(1)
        getGif(_currentPosition.value!!)
    }

    fun onBackButtonClicked(){
        _currentPosition.value = _currentPosition.value?.minus(1)
        getGif(_currentPosition.value!!)
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