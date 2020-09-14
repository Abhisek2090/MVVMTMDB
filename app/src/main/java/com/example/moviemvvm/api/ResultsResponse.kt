package com.example.moviemvvm.api

import com.google.gson.annotations.SerializedName

data class ResultsResponse<T> (
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<T>
)