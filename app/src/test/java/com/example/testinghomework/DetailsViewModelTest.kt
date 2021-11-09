package com.example.testinghomework

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testinghomework.ui.details.viewmodel.DetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    companion object {
        const val INITIAL_COUNTER = 100
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var detailsViewModel: DetailsViewModel

    @Before
    fun setup() {
        detailsViewModel = DetailsViewModel(INITIAL_COUNTER)
    }

    @Test
    fun testSetupCounterValue() {
        val observer = Observer<Int> { }
        val liveData = detailsViewModel.counter

        try {
            liveData.observeForever(observer)
            assertEquals(liveData.value, INITIAL_COUNTER)
        } finally {
            liveData.removeObserver(observer)
        }
    }

    @Test
    fun testIncrementButton() {
        val observer = Observer<Int> { }
        val liveData = detailsViewModel.counter

        try {
            liveData.observeForever(observer)

            detailsViewModel.incrementCounter()

            assertEquals(INITIAL_COUNTER + 1, liveData.value)
        } finally {
            liveData.removeObserver(observer)
        }
    }

    @Test
    fun testDecrementButton() {
        val observer = Observer<Int> { }
        val liveData = detailsViewModel.counter

        try {
            liveData.observeForever(observer)

            detailsViewModel.decrementCounter()
            detailsViewModel.decrementCounter()
            detailsViewModel.decrementCounter()

            assertEquals(INITIAL_COUNTER - 3, liveData.value)
        } finally {
            liveData.removeObserver(observer)
        }
    }
}