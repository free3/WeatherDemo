package lt.viskasnemokamai.weatherapp.ui.city_search

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import lt.viskasnemokamai.weatherapp.domain.CityWeather
import lt.viskasnemokamai.weatherapp.domain.mapper.Mapper
import lt.viskasnemokamai.weatherapp.domain.mapper.CityWeatherMapperImpl
import lt.viskasnemokamai.weatherapp.network.WeatherApiService
import lt.viskasnemokamai.weatherapp.network.dto.SearchByCityResponse
import retrofit2.adapter.rxjava2.Result

/**
 * Created by Greta Radisauskaite on 11/10/2020.
 */
open class CitySearchInteractor {

    //****************************************
    // Dependencies
    //****************************************

    internal var apiService: WeatherApiService = WeatherApiService.getInstance()

    internal var mapper: Mapper<SearchByCityResponse, CityWeather> =
        CityWeatherMapperImpl()

    interface OnSearchFinishedListener {
        fun onStartSearch()
        fun onNotFound()
        fun onFound(citiesWeather: ArrayList<CityWeather>, preserveHistory: Boolean)
        fun onError(throwable: Throwable?)
    }

    //****************************************
    // Public members
    //****************************************

    open fun lookup(
        listener: OnSearchFinishedListener,
        preserveHistory: Boolean,
        vararg cities: String
    ) {

        val requests: MutableList<Single<Result<SearchByCityResponse>>> = ArrayList()

        for (city in cities) {
            requests.add(apiService.searchWeather(city))
        }

        val cityWeather: ArrayList<CityWeather> = ArrayList()
        var error: Throwable? = null

        Single.concat(requests)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                listener.onStartSearch()
            }
            .doFinally {
                if (cityWeather.isEmpty() && error == null) {
                    listener.onNotFound()
                    return@doFinally
                }
                if (cityWeather.isEmpty() && error != null) {
                    listener.onError(error)
                    return@doFinally
                }

                listener.onFound(cityWeather, preserveHistory)
            }
            .subscribe(
                {
                    if (!it.isError && it.response()?.isSuccessful == true && it.response()
                            ?.body() != null) {
                        cityWeather.add(mapper.map(it.response()!!.body()!!))
                        return@subscribe
                    }
                    error = it.error()

                },
                {
                    it.printStackTrace()
                    listener.onError(it)
                })


    }


}