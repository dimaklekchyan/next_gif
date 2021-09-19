package com.klekchyan.nextgif.dagger

import com.klekchyan.nextgif.data.GifRepository
import com.klekchyan.nextgif.data.GifRepositoryImpl
import com.klekchyan.nextgif.network.ApiService
import com.klekchyan.nextgif.ui.ShowGifActivity
import com.klekchyan.nextgif.ui.ShowGifViewModel
import dagger.Binds
import dagger.Component
import dagger.Module

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: ShowGifActivity)

    val apiService: ApiService
    val gifRepository: GifRepository
    val viewModelFactory: ShowGifViewModel.ShowGifViewModelFactory

}

@Module(includes = [NetworkModule::class, AppBindModule::class])
class AppModule

@Module
interface AppBindModule{

    @Binds
    fun bindGifRepositoryImplToGifRepository(gifRepositoryImpl: GifRepositoryImpl) : GifRepository

}
