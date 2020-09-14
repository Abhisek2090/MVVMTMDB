package com.example.moviemvvm.movie.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieDao: MovieDao,
                                            private val movieRemoteDataSource: MovieRemoteDataSource) {
    fun observePagedMovies(connectivityAvailable: Boolean, title: String? = null,
                            coroutineScope: CoroutineScope)  =
                if(connectivityAvailable) {observeRemotePagedSets(title, coroutineScope)}
                else observeLocalPagedSets()

    private fun observeLocalPagedSets(title:String? = null): LiveData<PagedList<Movie>> {
        val dataSourceFactory =
            if(title == null) movieDao.getPagedMovies()
            else movieDao.getMoviesByTitle(title)

        return LivePagedListBuilder(dataSourceFactory,
                MoviePageDataSourceFactory.pagedListConfig()).build()
    }

    private fun observeRemotePagedSets(title: String?, coroutineScope: CoroutineScope): LiveData<PagedList<Movie>> {
        val dataSourceFactory = MoviePageDataSourceFactory(title, movieRemoteDataSource,movieDao, coroutineScope)
        return LivePagedListBuilder(dataSourceFactory,
            MoviePageDataSourceFactory.pagedListConfig()).build()
    }

    companion object {
        const val PAGE_SIZE = 10

        // For Singleton instantiation
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(dao:MovieDao, movieRemoteDataSource: MovieRemoteDataSource) =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(dao, movieRemoteDataSource).also { instance = it }
            }
    }
}