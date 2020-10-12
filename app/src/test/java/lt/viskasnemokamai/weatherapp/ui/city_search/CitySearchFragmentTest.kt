package lt.viskasnemokamai.weatherapp.ui.city_search

import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import lt.viskasnemokamai.weatherapp.ui.MainActivity
import lt.viskasnemokamai.weatherapp.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Created by Greta Radisauskaite on 11/10/2020.
 */

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class CitySearchFragmentTest {

    @Test
    fun onView_should_display_views() {

        // Arrange

        // Act
        var citySearchFragment = CitySearchFragment.newInstance()

        ActivityScenario.launch(MainActivity::class.java)
            .onActivity {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.flMain, citySearchFragment)
                    .commit()
            }

        // Assert
        onView(withId(R.id.etCitySearch)).check(matches(isDisplayed()))
        onView(withId(R.id.btnViewHistory)).check(matches(isDisplayed()))
    }

}