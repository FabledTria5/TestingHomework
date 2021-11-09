package com.example.testinghomework.ui.details.presenter

import com.example.testinghomework.ui.base.PresenterContract

interface PresenterDetailsContract : PresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}