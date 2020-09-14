package com.example.moviemvvm.movie.ui

import androidx.lifecycle.ViewModel
import com.example.moviemvvm.di.CoroutineScopeIO
import com.example.moviemvvm.movie.data.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val repository: MovieRepository,
                                        @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope) : ViewModel() {
    var connectivityAvailable: Boolean = false
    var title:String ? = null

    val movies by lazy {
        repository.observePagedMovies(
            connectivityAvailable, title, ioCoroutineScope)
    }

    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel();
    }
}