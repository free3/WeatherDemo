package lt.viskasnemokamai.weatherapp.ui.city_search_results

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import lt.viskasnemokamai.weatherapp.R
import lt.viskasnemokamai.weatherapp.domain.CityWeather
import lt.viskasnemokamai.weatherapp.ui.city_search_results.CitiesWeatherListAdapter.ViewHolder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import java.util.*
import kotlin.collections.ArrayList


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class CitiesWeatherListAdapterTest {

    lateinit var instance: CitiesWeatherListAdapter

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var resources: Resources

    @Mock
    lateinit var layoutInflater: LayoutInflater

    @Mock
    lateinit var viewInflated: View

    @Mock
    lateinit var tvCity: TextView

    @Mock
    lateinit var tvTemperature: TextView

    @Mock
    lateinit var tvDate: TextView

    @Mock
    lateinit var tvWeatherDesc: TextView

    var defaultTextSize = 10F

    internal lateinit var viewHolder: ViewHolder


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE)).thenReturn(layoutInflater)
        `when`(context.resources).thenReturn(resources)
        `when`(layoutInflater.inflate(R.layout.item_city_weather, null)).thenReturn(viewInflated)

        `when`(viewInflated.findViewById<TextView>(R.id.tvCity)).thenReturn(tvCity)
        `when`(viewInflated.findViewById<TextView>(R.id.tvTemperature)).thenReturn(tvTemperature)
        `when`(viewInflated.findViewById<TextView>(R.id.tvDate)).thenReturn(tvDate)
        `when`(viewInflated.findViewById<TextView>(R.id.tvWeatherDesc)).thenReturn(tvWeatherDesc)

        viewHolder = ViewHolder(tvCity, tvTemperature, tvDate, tvWeatherDesc)
        `when`(viewInflated.getTag()).thenReturn(viewHolder)
        `when`(tvWeatherDesc.textSize).thenReturn(defaultTextSize)
    }

    @Test
    fun getItemId_should_return_position() {

        // Arrange
        var cities = ArrayList<CityWeather>()
        cities.add(CityWeather())
        cities.add(CityWeather())
        cities.add(CityWeather())

        instance = CitiesWeatherListAdapter(context, cities)

        // Act
        var itemAtPosition = instance.getItemId(1)

        // Assert
        assertEquals(1, itemAtPosition)
    }

    @Test
    fun getItem_should_return_specified_item() {

        // Arrange
        var cities = ArrayList<CityWeather>()
        cities.add(CityWeather(name = "name0"))
        cities.add(CityWeather(name = "name1"))
        cities.add(CityWeather(name = "name2"))

        instance = CitiesWeatherListAdapter(context, cities)

        // Act
        var itemAtPosition = instance.getItem(2)

        // Assert
        assertEquals("name2", itemAtPosition.name)
    }

    @Test
    fun getCount_should_return_0_when_empty() {

        // Arrange
        var cities = ArrayList<CityWeather>()

        instance = CitiesWeatherListAdapter(context, cities)

        // Act
        var count = instance.count

        // Assert
        assertEquals(0, count)
    }

    @Test
    fun getCount_should_return_2_when_size_2() {

        // Arrange
        var cities = ArrayList<CityWeather>()
        cities.add(CityWeather())
        cities.add(CityWeather())

        instance = CitiesWeatherListAdapter(context, cities)

        // Act
        var count = instance.count

        // Assert
        assertEquals(2, count)
    }

    @Test
    fun getView_should_set_cold_theme_colors_when_temp_below_11() {

        // Arrange
        var cities = ArrayList<CityWeather>()
        var date = Date(6666666)
        cities.add(
            CityWeather(
                name = "name1",
                temp = 10,
                weatherDescription = "cloudy",
                date = date
            )
        )

        val nameColor = 111
        val temperatureColor = 222
        val dateColor = 333

        `when`(resources.getColor(R.color.colorYellow)).thenReturn(nameColor)
        `when`(resources.getColor(R.color.colorGreen)).thenReturn(temperatureColor)
        `when`(resources.getColor(R.color.colorRed)).thenReturn(dateColor)

        instance = CitiesWeatherListAdapter(context, cities)

        // Act
        var view = instance.getView(0, null, null)

        // Assert
        assertNotNull(view)
        verify(tvCity).setTextColor(nameColor)
        verify(tvTemperature).setTextColor(temperatureColor)
        verify(tvDate).setTextColor(dateColor)
    }

    @Test
    fun getView_should_set_intermediate_theme_colors_when_temp_between_10_and_20() {

        // Arrange
        var cities = ArrayList<CityWeather>()
        var date = Date(6666666)
        cities.add(
            CityWeather(
                name = "name1",
                temp = 19,
                weatherDescription = "cloudy",
                date = date
            )
        )

        val nameColor = 111
        val temperatureColor = 222
        val dateColor = 333

        `when`(resources.getColor(R.color.colorRed)).thenReturn(nameColor)
        `when`(resources.getColor(R.color.colorYellow)).thenReturn(temperatureColor)
        `when`(resources.getColor(R.color.colorGreen)).thenReturn(dateColor)

        instance = CitiesWeatherListAdapter(context, cities)

        // Act
        var view = instance.getView(0, null, null)

        // Assert
        assertNotNull(view)
        verify(tvCity).setTextColor(nameColor)
        verify(tvTemperature).setTextColor(temperatureColor)
        verify(tvDate).setTextColor(dateColor)
    }

    @Test
    fun getView_should_set_warm_theme_colors_when_temp_above_19() {

        // Arrange
        var cities = ArrayList<CityWeather>()
        var date = Date(6666666)
        cities.add(
            CityWeather(
                name = "name1",
                temp = 20,
                weatherDescription = "cloudy",
                date = date
            )
        )

        val nameColor = 111
        val temperatureColor = 222
        val dateColor = 333

        `when`(resources.getColor(R.color.colorGreen)).thenReturn(nameColor)
        `when`(resources.getColor(R.color.colorRed)).thenReturn(temperatureColor)
        `when`(resources.getColor(R.color.colorYellow)).thenReturn(dateColor)

        instance = CitiesWeatherListAdapter(context, cities)

        // Act
        var view = instance.getView(0, null, null)

        // Assert
        assertNotNull(view)
        verify(tvCity).setTextColor(nameColor)
        verify(tvTemperature).setTextColor(temperatureColor)
        verify(tvDate).setTextColor(dateColor)
    }

    @Test
    fun getView_should_fix_text_case_and_add_temp_degree_sign() {

        // Arrange
        var cities = ArrayList<CityWeather>()
        var date = Date(6666666)
        cities.add(
            CityWeather(
                name = "name1",
                temp = 20,
                weatherDescription = "cloudy",
                date = date
            )
        )

        `when`(resources.getColor(anyInt())).thenReturn(111)

        instance = CitiesWeatherListAdapter(context, cities)

        // Act
        var view = instance.getView(0, null, null)

        // Assert
        assertNotNull(view)
        verify(tvCity).text = "Name1"
        verify(tvWeatherDesc).text = "Cloudy"
        verify(tvTemperature).text = "20°"
    }

    @Test
    fun getView_should_not_crash_when_invalid_city_weather_provided() {

        // Arrange
        var cities = ArrayList<CityWeather>()
        cities.add(CityWeather())

        instance = CitiesWeatherListAdapter(context, cities)

        // Act
        var view = instance.getView(0, null, null)

        // Assert
        assertNotNull(view)
        verifyNoMoreInteractions(tvCity)
        verifyNoMoreInteractions(tvWeatherDesc)
        verifyNoMoreInteractions(tvTemperature)
        verifyNoMoreInteractions(tvDate)
    }

}
