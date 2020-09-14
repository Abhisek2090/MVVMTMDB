package com.example.moviemvvm.di


import com.example.moviemvvm.movie.ui.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMoviesFragment(): MoviesFragment


}
