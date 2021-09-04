package com.klekchyan.nextgif.dagger

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.klekchyan.nextgif.data.GifRepository
import com.klekchyan.nextgif.data.GifRepositoryImpl
import com.klekchyan.nextgif.network.ApiService
import com.klekchyan.nextgif.network.BASE_URL
import com.klekchyan.nextgif.ui.ShowGifActivity
import com.klekchyan.nextgif.ui.ShowGifViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
class NetworkModule{

    @Provides
    fun getApiService() : ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}

@Module
interface AppBindModule{

    @Binds
    fun bindGifRepositoryImplToGifRepository(gifRepositoryImpl: GifRepositoryImpl) : GifRepository

}
