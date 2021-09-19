package com.klekchyan.nextgif.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.klekchyan.nextgif.models.GifDomain
import com.klekchyan.nextgif.models.asGifDomain
import com.klekchyan.nextgif.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

enum class LoadingState{ LOADING, SUCCESSFULLY, FAILURE }

interface GifRepository {

    val currentPosition: LiveData<Int>
    val currentGif: LiveData<GifDomain>
    val loadingState: LiveData<LoadingState>

    suspend fun initialGif()
    suspend fun getNextGif()
    suspend fun getPreviousGif()
}

class GifRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : GifRepository {

    private val _currentPosition = MutableLiveData<Int>()
    private val _currentGif = MutableLiveData<GifDomain>()
    private val _loadingState = MutableLiveData<LoadingState>()

    override val currentPosition: LiveData<Int>
        get() = _currentPosition
    override val currentGif: LiveData<GifDomain>
        get() = _currentGif
    override val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val queue = HashMap<Int, GifDomain>()

    init{
        _currentPosition.value = 0
    }

    override suspend fun initialGif(){
        getNewGif(_currentPosition.value!!)
    }

    private suspend fun getNewGif(position: Int){
        _loadingState.value = LoadingState.LOADING
        withContext(Dispatchers.IO) {
            try {
                val gifDomain: GifDomain = apiService.randomGIF().asGifDomain()
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

    override suspend fun getNextGif() {
        val nextPosition = _currentPosition.value!!.plus(1)
        _currentPosition.value = nextPosition
        if(queue.containsKey(nextPosition)) {
            _currentGif.value = queue[nextPosition]
            _loadingState.value = LoadingState.SUCCESSFULLY
        } else {
            getNewGif(nextPosition)
        }
    }

    override suspend fun getPreviousGif(){
        val previousPosition = _currentPosition.value!!.minus(1)
        _currentPosition.value = previousPosition
        if(queue.containsKey(previousPosition)) {
            _currentGif.value = queue[previousPosition]
            _loadingState.value = LoadingState.SUCCESSFULLY
        } else {
            getNewGif(previousPosition)
        }
    }
}