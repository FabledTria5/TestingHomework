package com.example.testinghomework.ui.search.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.testinghomework.R
import com.example.testinghomework.databinding.ActivityMainBinding
import com.example.testinghomework.model.SearchResult
import com.example.testinghomework.repository.GitHubApi
import com.example.testinghomework.repository.GitHubRepository
import com.example.testinghomework.ui.details.view.DetailsActivity
import com.example.testinghomework.ui.search.presenter.PresenterSearchContract
import com.example.testinghomework.ui.search.presenter.SearchPresenter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), ViewSearchContract {

    companion object {
        const val BASE_URL = "https://api.github.com"
    }

    private lateinit var binding: ActivityMainBinding

    private var totalCount: Int = 0

    private val adapter = SearchResultAdapter()
    private val presenter: PresenterSearchContract = SearchPresenter(this, createRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setUi()
    }

    private fun setUi() {
        binding.toDetailsActivityButton.setOnClickListener {
            startActivity(DetailsActivity.getIntent(this, totalCount))
        }
        setQueryListener()
        setRecyclerListener()
    }

    private fun setQueryListener() {
        binding.searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEditText.text.toString()
                if (query.isNotBlank()) {
                    presenter.searchGithub(query)
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

    private fun createRepository(): GitHubRepository {
        return GitHubRepository(createRetrofit().create(GitHubApi::class.java))
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun displaySearchResults(searchResults: List<SearchResult>, totalCount: Int) {
        this.totalCount = totalCount
        binding.totalCountTextView.isVisible = true
        binding.totalCountTextView.text = getString(R.string.results_count, this.totalCount)
        adapter.updateResults(searchResults)
    }

    override fun displayError() {
        Toast.makeText(this, getString(R.string.undefined_error), Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

}