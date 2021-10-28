package com.example.testinghomework.ui.details.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testinghomework.R
import com.example.testinghomework.databinding.ActivityDetailsBinding
import com.example.testinghomework.ui.details.presenter.DetailsPresenter
import com.example.testinghomework.ui.details.presenter.PresenterDetailsContract
import java.util.*

class DetailsActivity : AppCompatActivity(), ViewDetailsContract {

    companion object {
        const val TOTAL_COUNT_EXTRA = "TOTAL_COUNT_EXTRA"

        fun getIntent(context: Context, totalCount: Int) =
            Intent(context, DetailsActivity::class.java).apply {
                putExtra(TOTAL_COUNT_EXTRA, totalCount)
            }
    }

    private lateinit var binding: ActivityDetailsBinding

    private val presenter: PresenterDetailsContract = DetailsPresenter(viewContract = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setUi()
    }

    private fun setUi() {
        val count = intent.getIntExtra(TOTAL_COUNT_EXTRA, 0)
        presenter.setCounter(count)
        setCountText(count = count)
        with(binding) {
            decrementButton.setOnClickListener { presenter.onDecrement() }
            incrementButton.setOnClickListener { presenter.onIncrement() }
        }
    }

    override fun setCount(count: Int) {
        setCountText(count = count)
    }

    private fun setCountText(count: Int) {
        binding.totalCountTextView.text =
            String.format(Locale.getDefault(), getString(R.string.results_count), count)
    }

}