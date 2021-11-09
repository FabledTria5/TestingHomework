package com.example.testinghomework.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class DetailsScreenTest {

    companion object {
        private const val TIMEOUT = 5000L
    }

    private val uiDevice = UiDevice.getInstance(getInstrumentation())

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val packageName = context.packageName

    @Before
    fun setup() {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @Test
    fun test_OpenDetailsScreen() {
        val toDetails = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "numberOfResults")),
                TIMEOUT
            )

        assertEquals(changedText.text, "Number of results: 0")
    }

    @Test
    fun test_DetailsScreenShowsSearchedResultsCount() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        val searchButton = uiDevice.findObject(By.res(packageName, "fabSearchButton"))
        val toDetails = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )

        editText.text = "UiAutomator"
        searchButton.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            ).text.toString()

        toDetails.click()

        val numberOfResults = uiDevice.wait(
            Until.findObject(By.res(packageName, "numberOfResults")),
            TIMEOUT
        )

        assertEquals(changedText, numberOfResults.text.toString())
    }

    @Test
    fun test_DetailsScreenIncrementButton() {
        val toDetails = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()

        val numberOfResults = uiDevice.wait(
            Until.findObject(By.res(packageName, "numberOfResults")),
            TIMEOUT
        )

        val incrementButton = uiDevice.findObject(By.res(packageName, "incrementButton"))
        incrementButton.click()

        assertEquals("Number of results: 1", numberOfResults.text.toString())
    }

    @Test
    fun test_DetailsScreenDecrementButton() {
        val toDetails = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()

        val numberOfResults = uiDevice.wait(
            Until.findObject(By.res(packageName, "numberOfResults")),
            TIMEOUT
        )

        val decrementButton = uiDevice.findObject(By.res(packageName, "decrementButton"))
        decrementButton.click()
        decrementButton.click()
        decrementButton.click()

        assertEquals("Number of results: -3", numberOfResults.text.toString())
    }

}