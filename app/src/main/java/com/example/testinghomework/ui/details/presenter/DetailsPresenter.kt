package com.example.testinghomework.ui.details.presenter

import com.example.testinghomework.ui.details.view.ViewDetailsContract

class DetailsPresenter internal constructor(
    private val viewContract: ViewDetailsContract,
    private var count: Int = 0
): PresenterDetailsContract {

    override fun attach() {

    }

    override fun setCounter(count: Int) {
        this.count = count
    }

    override fun onIncrement() {
        count++
        viewContract.setCount(count = count)
    }

    override fun onDecrement() {
        count--
        viewContract.setCount(count = count)
    }

    override fun detach() {
        count = 0
    }

}