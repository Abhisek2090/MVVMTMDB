package com.example.moviemvvm.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MovieAPI

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScopeIO