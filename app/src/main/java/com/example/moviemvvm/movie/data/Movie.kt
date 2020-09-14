package com.example.moviemvvm.movie.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @field:SerializedName("id")
    val id:Int,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("poster_path")
    val poster_path:String ? = null,
    @field:SerializedName("vote_count")
    val vote_count:Int? = null
) {
    override fun toString() = title
}