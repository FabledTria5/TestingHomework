package com.example.testinghomework

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testinghomework.model.ScreenState
import com.example.testinghomework.model.SearchResponse
import com.example.testinghomework.repository.FakeGitHubRepository
import com.example.testinghomework.ui.search.viewmodel.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
class SearchViewModelTest {

    companion object {
        private const val SEARCH_QUERY = "some query"
        private const val ERROR_TEXT = "Search results or search count are null"
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var searchViewModel: SearchViewModel

    @Mock
    private lateinit var repository: FakeGitHubRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        searchViewModel = SearchViewModel(repository)
    }

    @Test
    fun liveData_TestReturnValueIsNotNull() = testCoroutineRule.runBlockingTest {
        val observer = Observer<ScreenState> { }
        val liveData = searchViewModel.repositories

        `when`(repository.searchGithubAsync(SEARCH_QUERY)).thenReturn(
            SearchResponse(1, listOf())
        )

        try {
            liveData.observeForever(observer)
            searchViewModel.searchGithub(SEARCH_QUERY)
            assertNotNull(liveData.value)
        } finally {
            liveData.removeObserver(observer)
        }
    }

    @Test
    fun coroutines_TestReturnValueIsError() = testCoroutineRule.runBlockingTest {
        val observer = Observer<ScreenState> { }
        val liveData = searchViewModel.repositories

        `when`(repository.searchGithubAsync(SEARCH_QUERY)).thenReturn(
            SearchResponse(null, listOf())
        )

        try {
            liveData.observeForever(observer)
            searchViewModel.searchGithub(SEARCH_QUERY)

            val value = liveData.value as ScreenState.Error
            assertEquals(ERROR_TEXT, value.error.message)
        } finally {
            liveData.removeObserver(observer)
        }
    }

    @Test
    fun coroutines_TestReturnValueIsSuccessful() = testCoroutineRule.runBlockingTest {
        val testTotalCount = 10
        val observer = Observer<ScreenState> { }
        val liveData = searchViewModel.repositories

        `when`(repository.searchGithubAsync(SEARCH_QUERY)).thenReturn(
            SearchResponse(testTotalCount, listOf())
        )

        try {
            liveData.observeForever(observer)
            searchViewModel.searchGithub(SEARCH_QUERY)

            val value = liveData.value as ScreenState.Success
            assertEquals(value.searchResponse.totalCount, testTotalCount)
        } finally {
            liveData.removeObserver(observer)
        }
    }

}