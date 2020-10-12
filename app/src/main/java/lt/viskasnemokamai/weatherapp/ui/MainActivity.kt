package lt.viskasnemokamai.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import lt.viskasnemokamai.weatherapp.R
import lt.viskasnemokamai.weatherapp.domain.CityWeather
import lt.viskasnemokamai.weatherapp.ui.city_search.CitySearchFragment
import lt.viskasnemokamai.weatherapp.ui.city_search_results.CitiesWeatherFragment

class MainActivity : AppCompatActivity(), CitySearchFragment.OnFragmentInteractionListener {

    //****************************************
    // Life Cycle overrides
    //****************************************

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(
            R.id.flMain,
            CitySearchFragment.newInstance(), null ).commit()
    }

    //****************************************
    // Fragment callbacks
    //****************************************

    override fun onCitySearchCompleted(cityWeather: ArrayList<CityWeather>) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flMain, CitiesWeatherFragment.newInstance(cityWeather), null)
            .addToBackStack(null).commit()
    }

    //****************************************
    // Local utils
    //****************************************

}
