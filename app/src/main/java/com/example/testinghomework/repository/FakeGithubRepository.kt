package com.example.testinghomework.repository

import com.example.testinghomework.model.SearchResponse
import com.example.testinghomework.model.SearchResult
import retrofit2.Response
import kotlin.random.Random

internal class FakeGitHubRepository : RepositoryContract {

    override fun searchGithub(
        query: String,
        callback: RepositoryCallback
    ) {
        callback.handleGitHubResponse(Response.success(getFakeResponse()))
    }

    private fun getFakeResponse(): SearchResponse {
        val list = mutableListOf<SearchResult>()
        (1..100).forEachIndexed { index, _ ->
            list.add(SearchResult(
                id = index,
                name = "Name: $index",
                fullName = "Full Name: $index",
                private = Random.nextBoolean(),
                description = "Description: $index",
                updatedAt = "Updated: $index",
                size = index,
                stargazersCount = Random.nextInt(100),
                language = "",
                hasWiki = Random.nextBoolean(),
                archived = Random.nextBoolean(),
                score = index.toDouble()
            ))
        }
        return SearchResponse(list.size, list)
    }


}