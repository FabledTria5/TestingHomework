package com.example.testinghomework.repository

import com.example.testinghomework.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GitHubApi {
    @Headers("Accept: application/vnd.github.mercy-preview+json")
    @GET("search/repositories")
    fun searchGithub(@Query("q") term: String?): Call<SearchResponse?>?

    @Headers("Accept: application/vnd.github.mercy-preview+json")
    @GET("search/repositories")
    suspend fun searchGithubAsync(@Query("q") term: String?): SearchResponse

}