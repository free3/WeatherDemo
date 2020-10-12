package lt.viskasnemokamai.weatherapp.domain

import java.io.Serializable
import java.util.*

/**
 * Created by Greta Radisauskaite on 08/10/2020.
 */
data class CityWeather(
    val name: String? = null,
    val weatherDescription: String? = null,
    val weatherIcon: String? = null,
    val temp: Int? = null,
    val tempMin: Int? = null,
    val tempMax: Int? = null,
    val date: Date? = null
) : Serializable

// ToDo: according to task's description, we need tempMin, tempMax. But why ?