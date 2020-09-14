package com.example.moviemvvm.di

import android.app.Application
import com.example.moviemvvm.BuildConfig
import com.example.moviemvvm.api.AuthInterceptor
import com.example.moviemvvm.api.MovieService
import com.example.moviemvvm.data.AppDatabase
import com.example.moviemvvm.movie.data.MovieRemoteDataSource
import com.example.moviemvvm.movie.data.MovieRepository.Companion.getInstance
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideMovieService(@MovieAPI okHttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory)
     = provideService(okHttpClient, converterFactory, MovieService::class.java)

    @Singleton
    @Provides
    fun provideMovieRemoteDataService(movieService:MovieService)
        = MovieRemoteDataSource(movieService)

    @Singleton
    @Provides
    fun provideDb(app:Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    @MovieAPI
    @Provides
    fun providePrivateOkHttpClient(
        upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder()
            .build()
    }


    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MovieService.ENDPOINT)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun <T> provideService(okhttpClient: OkHttpClient,
                                   converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }

}