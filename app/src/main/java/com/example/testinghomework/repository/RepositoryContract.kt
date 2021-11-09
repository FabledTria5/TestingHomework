package com.example.testinghomework.repository

import com.example.testinghomework.model.SearchResponse

interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )

    suspend fun searchGithubAsync(query: String): SearchResponse
}