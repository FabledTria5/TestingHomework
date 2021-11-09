package com.example.testinghomework.ui.search.viewmodel

import androidx.lifecycle.*
import com.example.testinghomework.model.ScreenState
import com.example.testinghomework.model.SearchResponse
import com.example.testinghomework.repository.RepositoryContract
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: RepositoryContract,
) : ViewModel() {

    private val _repositories = MutableLiveData<ScreenState>()
    val repositories: LiveData<ScreenState> = _repositories

    fun searchGithub(searchQuery: String) = viewModelScope.launch {
        _repositories.postValue(ScreenState.Loading)
        val searchResponse = repository.searchGithubAsync(query = searchQuery)
        if (searchResponse.totalCount != null && searchResponse.searchResults != null) {
            _repositories.postValue(ScreenState.Success(searchResponse))
        } else {
            _repositories.postValue(ScreenState.Error(
                Throwable("Search results or search count are null")
            ))
        }
    }

    class SearchViewModelFactory(
        private val repository: RepositoryContract,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            SearchViewModel(repository) as T
    }
}