package lt.viskasnemokamai.weatherapp.ui.city_search

import lt.viskasnemokamai.weatherapp.domain.CityWeather

/**
 * Created by Greta Radisauskaite on 11/10/2020.
 */
interface CitySearchView {

    fun showProgress()

    fun hideProgress()

    fun setNotFound()

    fun setError()

    fun navigateToResults(cityWeather: ArrayList<CityWeather>)

}