package com.example.testinghomework.ui.search.presenter

import com.example.testinghomework.ui.base.PresenterContract

internal interface PresenterSearchContract : PresenterContract {
    fun searchGithub(searchQuery: String)
}