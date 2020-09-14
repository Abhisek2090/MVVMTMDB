package com.example.moviemvvm.di

import com.facebook.stetho.okhttp3.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class CoreDataModule {

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) : OkHttpClient =
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

    @Provides
    fun provideLoggingInterceptor() =
            HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) BODY  else NONE }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun providesGsonConverterFactory(gson:Gson) : GsonConverterFactory =
        GsonConverterFactory.create(gson)
}