package com.example.moviemvvm.movie.data

import androidx.paging.PageKeyedDataSource
import com.example.moviemvvm.data.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MoviePageDataSource @Inject constructor(
    private val title:String? = null,
    private val dataSource: MovieRemoteDataSource,
    private val dao: MovieDao,
    private val scope: CoroutineScope): PageKeyedDataSource<Int, Movie>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,   callback: LoadInitialCallback<Int, Movie>) {
        fetchData(title,1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key
        fetchData(title, params.key, params.requestedLoadSize) {
            callback.onResult(it, page -1)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key
        fetchData(title, params.key, params.requestedLoadSize) {
            callback.onResult(it, page+1)
        }
    }

    private fun fetchData(title: String?, page:Int, pageSize:Int, callback:(List<Movie>) -> Unit) {

        scope.launch(getJobErrorHandler()) {
            val response = dataSource.fetchMovies(title, "en", "desc", page, pageSize)
            if(response.status == Result.Status.SUCCESS) {
               val results = response.data!!.results
               dao.insertAll(results)
               callback(results)
            }else if(response.status == Result.Status.ERROR) {
                postError(response.message!!)
            }
        }

    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler{_, e ->
        postError(e.message ?: e.toString())
    }}

    private fun postError(message: String) {
        Timber.e("An error happened: $message")
        // TODO network error handling
        //networkState.postValue(NetworkState.FAILED)
    }