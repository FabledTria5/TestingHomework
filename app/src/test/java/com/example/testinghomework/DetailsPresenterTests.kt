package com.example.testinghomework

import com.example.testinghomework.ui.details.presenter.DetailsPresenter
import com.example.testinghomework.ui.details.presenter.PresenterDetailsContract
import com.example.testinghomework.ui.details.view.ViewDetailsContract
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailsPresenterTests {

    @Mock
    private lateinit var viewDetailsContract: ViewDetailsContract

    private lateinit var detailsPresenter: PresenterDetailsContract

    private val count: Int = 50

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        detailsPresenter = DetailsPresenter(viewContract = viewDetailsContract, count = count)
    }
    
    @Test
    fun presenterIncrement() {
        detailsPresenter.onIncrement()
        verify(viewDetailsContract).setCount(count + 1)
    }

    @Test
    fun presenterDecrement() {
        detailsPresenter.onDecrement()
        verify(viewDetailsContract).setCount(count - 1)
    }

}