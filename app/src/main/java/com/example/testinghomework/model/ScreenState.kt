package com.example.testinghomework.model

sealed class ScreenState {
    object Loading : ScreenState()
    data class Success(val searchResponse: SearchResponse) : ScreenState()
    data class Error(val error: Throwable) : ScreenState()
}