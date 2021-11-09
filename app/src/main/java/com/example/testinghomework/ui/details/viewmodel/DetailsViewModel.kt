package com.example.testinghomework.ui.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailsViewModel(count: Int) : ViewModel() {

    private val _counter = MutableLiveData(count)
    val counter: LiveData<Int> = _counter

    fun incrementCounter() {
        _counter.value = _counter.value?.plus(1)
    }

    fun decrementCounter() {
        _counter.value = _counter.value?.minus(1)
    }

    class DetailsViewModelFactory(
        private val count: Int,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            DetailsViewModel(count) as T
    }

}