package com.example.moviemvvm.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.moviemvvm.data.AppDatabase
import com.example.moviemvvm.movie.data.Movie
import com.example.moviemvvm.utils.DATA_FILENAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        withContext(Dispatchers.IO) {

            try {
                applicationContext.assets.open(DATA_FILENAME).use { inputStream ->
                    com.google.gson.stream.JsonReader(inputStream.reader()).use { jsonReader ->
                        val type = object : TypeToken<List<Movie>>() {}.type
                        val list: List<Movie> = Gson().fromJson(jsonReader, type)

                        AppDatabase.getInstance(applicationContext).movieDao().insertAll(list)

                        Result.success()
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error seeding database")
                Result.failure()
            }
        }
    }
}