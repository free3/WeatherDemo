package lt.viskasnemokamai.weatherapp.network.dto

data class SearchByCityResponse(
    val visibility: Int? = null,
    val timezone: Int? = null,
    val main: Main? = null,
    val clouds: Clouds? = null,
    val sys: Sys? = null,
    val dt: Int? = null,
    val coord: Coord? = null,
    val weather: List<WeatherItem?>? = null,
    val name: String? = null,
    val cod: Int? = null,
    val message: String? = null,
    val id: Int? = null,
    val base: String? = null,
    val wind: Wind? = null
)

// ToDo: we definitely can get rid of properties that we don't use
