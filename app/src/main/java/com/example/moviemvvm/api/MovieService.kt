package com.example.moviemvvm.api
import com.example.moviemvvm.movie.data.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    companion object {
        const val ENDPOINT = "https://api.themoviedb.org/3/discover/"
    }

    @GET("movie")
    suspend fun getMovies(
        @Query("title") title: String? = null,
        @Query("language") language: String? = null,
        @Query("sort_by") sort_by: String? = null,
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("api_key") api_key: String? = null
    ): Response<ResultsResponse<Movie>>

}