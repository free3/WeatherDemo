package lt.viskasnemokamai.weatherapp.network

/**
 * Created by Greta Radisauskaite on 2020-10-08.
 */
class WeatherApiServiceConstants {

    companion object {

        /**
         * App Id
         */
        const val API_REQUEST_APP_ID = "7587eaff3affbf8e56a81da4d6c51d06"

        /**
         * Base url of Weather API
         */
        const val API_REQUEST_BASE_URL = "http://api.openweathermap.org/data/2.5/"

        /**
         * Url of weather description icon
         */
        fun DESCRIPTION_ICON_URL(icon: String): String {
            return "http://openweathermap.org/img/wn/${icon}@2x.png"
        }

    }

}