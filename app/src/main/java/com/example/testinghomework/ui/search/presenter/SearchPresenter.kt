package com.example.testinghomework.ui.search.presenter

import com.example.testinghomework.model.SearchResponse
import com.example.testinghomework.repository.GitHubRepository
import com.example.testinghomework.repository.RepositoryCallback
import com.example.testinghomework.ui.search.view.ViewSearchContract
import retrofit2.Response

class SearchPresenter internal constructor(
    private val viewContract: ViewSearchContract,
    private val repository: GitHubRepository
): PresenterSearchContract, RepositoryCallback {

    override fun searchGithub(searchQuery: String) {
        repository.searchGithub(searchQuery, this)
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCount = searchResponse?.totalCount
            if (searchResults != null && totalCount != null) {
                viewContract.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract.displayError("Search results or total count are null")
            }
        } else {
            viewContract.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleGitHubError() {
        viewContract.displayError()
    }

}