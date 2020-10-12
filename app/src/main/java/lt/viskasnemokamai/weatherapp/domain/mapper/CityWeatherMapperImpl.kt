package lt.viskasnemokamai.weatherapp.domain.mapper

import lt.viskasnemokamai.weatherapp.domain.CityWeather
import lt.viskasnemokamai.weatherapp.network.dto.SearchByCityResponse
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Greta Radisauskaite on 09/10/2020.
 */
open class CityWeatherMapperImpl : Mapper<SearchByCityResponse, CityWeather> {

    override fun map(source: SearchByCityResponse): CityWeather {

        // ToDo in what circumstances WeatherItems would contain more than 1 item ?
        var weatherDescription: String = source.weather?.get(0)?.description ?: ""
        var weatherIcon: String? = source.weather?.get(0)?.icon
        var temp: Int? = source.main?.temp?.roundToInt()
        var tempMin: Int? = source.main?.tempMin?.roundToInt()
        var tempMax: Int? = source.main?.tempMax?.roundToInt()
        var time: Date? = null
        if (source.dt?.toLong() != null) {
            time = Date(source.dt.toLong() * 1000L)
        }

        return CityWeather(
            name = source.name,
            weatherDescription = weatherDescription,
            weatherIcon = weatherIcon,
            temp = temp,
            tempMin = tempMin,
            tempMax = tempMax,
            date = time
        )
    }

}