package com.example.moviemvvm.movie.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class MoviePageDataSourceFactory @Inject constructor(
           private val title:String? = null,
            private val remoteDataSource: MovieRemoteDataSource,
            private val dao: MovieDao,
            private val scope:CoroutineScope) : DataSource.Factory<Int, Movie>() {

    private val liveData = MutableLiveData<MoviePageDataSource>()

    override fun create(): DataSource<Int, Movie> {
      val source = MoviePageDataSource(title, remoteDataSource, dao, scope)
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 10

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()

    }
}