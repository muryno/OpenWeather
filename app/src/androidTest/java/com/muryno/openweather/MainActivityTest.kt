package com.muryno.openweather

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    @Rule
    @JvmField
    var mActivityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun displayScreenTitle() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.muryno.openweather", appContext.packageName)
    }

    @Test
    fun validateEditText() {
        onView(withId(R.id.location_input_field)).perform(ViewActions.typeText("location")).check(matches(withText("location")))
    }

    @Test
    fun validateEditHintText() {
        onView(withId(R.id.location_input_field))
            .check(matches(withHint(R.string.enter_location)))
    }



    @Test
    fun test_search_input_field() {
        onView(withId(R.id.submit_button))
            .check(matches(withText(R.string.go)))
            .perform(click())
    }


}