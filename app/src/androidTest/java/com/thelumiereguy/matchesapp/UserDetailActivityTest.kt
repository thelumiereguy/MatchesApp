package com.thelumiereguy.matchesapp


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.thelumiereguy.matchesapp.ui.activities.HomeActivity
import com.thelumiereguy.matchesapp.ui.activities.UserDetailActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(
    AndroidJUnit4::class
)
class UserDetailActivityTest {

    @get:Rule
    var mActivityRule: ActivityTestRule<HomeActivity> =
        ActivityTestRule(HomeActivity::class.java)


    @Test
    fun ensureTextChangesWork() {
        onView(withId(R.id.btn_accept)).perform(click())
    }
}