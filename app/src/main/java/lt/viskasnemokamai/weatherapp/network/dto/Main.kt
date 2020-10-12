package lt.viskasnemokamai.weatherapp.network.dto

data class Main(
    val temp: Double? = null,
    val tempMin: Double? = null,
    val humidity: Int? = null,
    val pressure: Int? = null,
    val feelsLike: Double? = null,
    val tempMax: Double? = null
)
