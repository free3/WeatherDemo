package lt.viskasnemokamai.weatherapp.ui.city_search_results

import android.content.Context
import lt.viskasnemokamai.weatherapp.R

/**
 * Created by Greta Radisauskaite on 09/10/2020.
 */
class ColdWeatherAppearanceStrategy
    (override var context: Context) :
    AppearanceStrategy {


    override fun cityNameColor(): Int {
        return getColor(R.color.colorYellow)
    }

    override fun temperatureColor(): Int {
        return getColor(R.color.colorGreen)
    }

    override fun dateColor(): Int {
        return getColor(R.color.colorRed)
    }
}