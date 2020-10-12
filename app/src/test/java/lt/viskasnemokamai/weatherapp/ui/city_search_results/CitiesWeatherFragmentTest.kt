package lt.viskasnemokamai.weatherapp.ui.city_search_results


import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import lt.viskasnemokamai.weatherapp.ui.MainActivity
import lt.viskasnemokamai.weatherapp.R
import lt.viskasnemokamai.weatherapp.domain.CityWeather
import lt.viskasnemokamai.weatherapp.TestUtils.Companion.withIndex
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.text.SimpleDateFormat

/**
 * Created by Greta Radisauskaite on 11/10/2020.
 */

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class CitiesWeatherFragmentTest {

    private lateinit var cities: ArrayList<CityWeather>

    private var city1 = CityWeather(
        name = "Palanga",
        temp = 10,
        weatherIcon = "icon1",
        weatherDescription = "Thunderstorm",
        date = SimpleDateFormat("yyyy-MM-dd").parse("2020-10-11")
    )

    private var city2 = CityWeather(
        name = "Vilnius",
        temp = 20,
        weatherIcon = "icon2",
        weatherDescription = "Blah blah blah",
        date = SimpleDateFormat("yyyy-MM-dd").parse("2020-10-12")
    )

    @Before
    fun setUp() {
        cities = ArrayList()
    }

    @Test
    fun onView_should_display_when_1_item_provided() {

        // Arrange
        cities.add(city1)

        // Act
        var citiesWeatherFragment = CitiesWeatherFragment.newInstance(cities)

        ActivityScenario.launch(MainActivity::class.java)
            .onActivity {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.flMain, citiesWeatherFragment)
                    .commit()
            }

        // Assert
        onView(withId(R.id.tvCity)).check(matches(isDisplayed()))
        onView(withId(R.id.tvWeatherDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTemperature)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDate)).check(matches(isDisplayed()))
    }

    @Test
    fun onView_should_display_when_multiple_items_provided() {

        // Arrange
        cities.add(city1)
        cities.add(city2)

        // Act
        var citiesWeatherFragment = CitiesWeatherFragment.newInstance(cities)

        ActivityScenario.launch(MainActivity::class.java)
            .onActivity {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.flMain, citiesWeatherFragment)
                    .commit()
            }

        // Assert
        onView(withIndex(withId(R.id.tvCity), 0)).check(matches(isDisplayed()))
        onView(withIndex(withId(R.id.tvWeatherDesc), 0)).check(matches(isDisplayed()))
        onView(withIndex(withId(R.id.tvTemperature), 0)).check(matches(isDisplayed()))
        onView(withIndex(withId(R.id.tvDate), 0)).check(matches(isDisplayed()))

        onView(withIndex(withId(R.id.tvCity), 1)).check(matches(isDisplayed()))
        onView(withIndex(withId(R.id.tvWeatherDesc), 1)).check(matches(isDisplayed()))
        onView(withIndex(withId(R.id.tvTemperature), 1)).check(matches(isDisplayed()))
        onView(withIndex(withId(R.id.tvDate), 1)).check(matches(isDisplayed()))
    }

    @Test
    fun onDestroyView_should_release_resources() {

        // Arrange
        cities.add(city1)

        // Act
        var citiesWeatherFragment = CitiesWeatherFragment.newInstance(cities)

        var scenario = ActivityScenario.launch(MainActivity::class.java)
            .onActivity {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.flMain, citiesWeatherFragment)
                    .commit()
            }

        scenario.moveToState(Lifecycle.State.DESTROYED)

        // Assert
        Assert.assertNull(citiesWeatherFragment._binding)
    }
}