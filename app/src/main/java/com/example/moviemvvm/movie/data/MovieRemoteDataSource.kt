package com.example.moviemvvm.movie.data

import com.example.moviemvvm.BuildConfig
import com.example.moviemvvm.api.BaseDataSource
import com.example.moviemvvm.api.MovieService
import javax.inject.Inject

/**
 * Works with the Lego API to get data.
 */

class MovieRemoteDataSource @Inject constructor (private val movieService: MovieService):BaseDataSource() {

    suspend fun fetchMovies(title:String?, language:String?, sort_by:String?, page:Int?, pageSize:Int?) =
        getResult { movieService.getMovies(title, language, sort_by, page, pageSize, BuildConfig.API_DEVELOPER_TOKEN)}

}

