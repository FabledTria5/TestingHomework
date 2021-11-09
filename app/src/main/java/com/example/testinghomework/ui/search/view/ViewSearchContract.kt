package com.example.testinghomework.ui.search.view

import com.example.testinghomework.model.SearchResult

interface ViewSearchContract {
    fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    )

    fun displayError()
    fun displayError(error: String)
}