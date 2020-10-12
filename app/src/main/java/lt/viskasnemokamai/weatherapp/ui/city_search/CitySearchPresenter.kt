package lt.viskasnemokamai.weatherapp.ui.city_search

import lt.viskasnemokamai.weatherapp.dao.AppDatabase
import lt.viskasnemokamai.weatherapp.domain.CitySearchHistoryItem
import lt.viskasnemokamai.weatherapp.domain.CityWeather
import lt.viskasnemokamai.weatherapp.utils.Constants
import lt.viskasnemokamai.weatherapp.utils.DateTime
import kotlin.collections.ArrayList

/**
 * Created by Greta Radisauskaite on 11/10/2020.
 */
open class CitySearchPresenter(
    var citySearchView: CitySearchView?,
    var appDatabase: AppDatabase?,
    val citySearchInteractor: CitySearchInteractor
) : CitySearchInteractor.OnSearchFinishedListener {

    //****************************************
    // Dependencies
    //****************************************

    internal var dateTime: DateTime = DateTime()

    //****************************************
    // Public members
    //****************************************

    /**
     * Executes city search request. Uses [CitySearchPresenter.citySearchView] as callbacks
     *
     * @param city to lookup
     * @return true if request will be executed; false if pre-checks failed
     */
    open fun searchCity(city: String?): Boolean {

        if (city == null || city.isEmpty()) {
            return false
        }

        citySearchInteractor.lookup(this, true, city)

        return true
    }

    /**
     * Retrieves local search history and executes multiple city search requests. Uses
     * [CitySearchPresenter.citySearchView] as callbacks
     *
     * @return true if request will be executed; false if pre-checks failed
     */
    open fun searchHistory(): Boolean {

        var history =
            appDatabase?.cityWeatherHistoryDao()?.getHistory()

        if (history == null || history.isEmpty()) {
            return false
        }

        citySearchInteractor.lookup(this, false, *history.map { it.name }.toTypedArray())

        return true
    }

    /**
     * Cancels callbacks in case host is destroyed
     */
    fun onDestroy() {
        citySearchView = null
        appDatabase = null
    }

    //****************************************
    // CitySearchInteractor.OnSearchFinishedListener overrides
    //****************************************

    override fun onStartSearch() {
        citySearchView?.showProgress()
    }

    override fun onNotFound() {
        citySearchView?.hideProgress()

        citySearchView?.setNotFound()
    }

    override fun onFound(citiesWeather: ArrayList<CityWeather>, preserveHistory: Boolean) {
        citySearchView?.hideProgress()

        citySearchView?.navigateToResults(citiesWeather)

        if (preserveHistory && citiesWeather.isNotEmpty()) {
            var cityHistoryItem =
                CitySearchHistoryItem(citiesWeather[0].name!!, dateTime.getDateTime().time)
            appDatabase?.cityWeatherHistoryDao()
                ?.addHistoryPreserveLimit(cityHistoryItem, Constants.CITY_SEARCH_HISTORY_LIMIT)
        }

    }

    override fun onError(throwable: Throwable?) {
        citySearchView?.hideProgress()

        citySearchView?.setError()
    }

}