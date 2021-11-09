package com.example.testinghomework.ui.details.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.testinghomework.R
import com.example.testinghomework.databinding.ActivityDetailsBinding
import com.example.testinghomework.ui.details.viewmodel.DetailsViewModel
import java.util.*

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val TOTAL_COUNT_EXTRA = "TOTAL_COUNT_EXTRA"

        fun getIntent(context: Context, totalCount: Int) =
            Intent(context, DetailsActivity::class.java).apply {
                putExtra(TOTAL_COUNT_EXTRA, totalCount)
            }
    }

    private lateinit var binding: ActivityDetailsBinding

    private val count: Int by lazy { intent.getIntExtra(TOTAL_COUNT_EXTRA, 0) }
    private val viewModelFactory by lazy { DetailsViewModel.DetailsViewModelFactory(count) }
    private val detailsViewModel: DetailsViewModel by viewModels(factoryProducer = { viewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setUi()
    }

    private fun setUi() {
        detailsViewModel.counter.observe(this) {
            setCountText(it)
        }
        with(binding) {
            decrementButton.setOnClickListener { detailsViewModel.decrementCounter() }
            incrementButton.setOnClickListener { detailsViewModel.incrementCounter() }
        }
    }

    private fun setCountText(count: Int) {
        binding.numberOfResults.text =
            String.format(Locale.getDefault(), getString(R.string.results_count), count)
    }

}