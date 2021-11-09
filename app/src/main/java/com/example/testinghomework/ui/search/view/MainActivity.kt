package com.example.testinghomework.ui.search.view

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.testinghomework.BuildConfig
import com.example.testinghomework.R
import com.example.testinghomework.databinding.ActivityMainBinding
import com.example.testinghomework.model.ScreenState
import com.example.testinghomework.model.SearchResult
import com.example.testinghomework.repository.FakeGitHubRepository
import com.example.testinghomework.repository.GitHubApi
import com.example.testinghomework.repository.GitHubRepository
import com.example.testinghomework.ui.details.view.DetailsActivity
import com.example.testinghomework.ui.search.viewmodel.SearchViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"

        const val BASE_URL = "https://api.github.com"

        const val FAKE = "FAKE"
    }

    private lateinit var binding: ActivityMainBinding

    private var totalCount: Int = 0

    private val adapter = SearchResultAdapter()

    private val viewModelFactory = SearchViewModel.SearchViewModelFactory(createRepository())

    private val searchViewModel: SearchViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setUi()
        observeResults()
    }

    private fun setUi() {
        binding.toDetailsActivityButton.setOnClickListener {
            startActivity(DetailsActivity.getIntent(this, totalCount))
        }
        setSearchListener()
        setQueryListener()
        setRecyclerListener()
    }

    private fun setSearchListener() {
        binding.fabSearchButton.setOnClickListener {
            val searchQuery = binding.searchEditText.text.toString()
            if (searchQuery.isNotEmpty()) searchViewModel.searchGithub(searchQuery)
        }
    }

    private fun setQueryListener() {
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEditText.text.toString()
                if (query.isNotBlank()) {
                    searchViewModel.searchGithub(query)
                    return@setOnEditorActionListener true
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.enter_search_word),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnEditorActionListener false
                }
            }
            false
        }
    }

    private fun setRecyclerListener() {
        with(binding) {
            recyclerView.adapter = adapter
        }
    }

    private fun observeResults() {
        searchViewModel.repositories.observe(this) {
            when (it) {
                is ScreenState.Error -> displayError(it.error)
                is ScreenState.Success -> displaySearchResults(it.searchResponse.searchResults,
                    it.searchResponse.totalCount)
                else -> Unit
            }
        }
    }

    private fun createRepository() = if (BuildConfig.TYPE == FAKE)
        FakeGitHubRepository()
    else
        GitHubRepository(createRetrofit().create(GitHubApi::class.java))

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun displaySearchResults(searchResults: List<SearchResult>?, totalCount: Int?) {
        this.totalCount = totalCount ?: 0
        binding.totalCountTextView.isVisible = true
        binding.totalCountTextView.text = getString(R.string.results_count, this.totalCount)
        adapter.updateResults(searchResults ?: listOf())
    }

    private fun displayError(error: Throwable) {
        Log.e(TAG, "displayError: $error")
        Toast.makeText(this, getString(R.string.undefined_error), Toast.LENGTH_SHORT).show()
    }

}