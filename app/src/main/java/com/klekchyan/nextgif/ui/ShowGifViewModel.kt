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

    private val _randomGif = MutableLiveData<GifDomain>()
    val randomGif: LiveData<GifDomain>
        get() = _randomGif

    private val _listOfGifs = MutableLiveData<List<GifDomain>>()
    val listOfGifs: LiveData<List<GifDomain>>
        get() = _listOfGifs

    init {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = gifRepository.getGifsFromSection("latest", 0)
                withContext(Dispatchers.Main) {
                    _listOfGifs.value = response
                }
            }
        } catch (ex: Exception){
            Timber.d(ex)
        }
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