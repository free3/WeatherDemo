package lt.viskasnemokamai.weatherapp.ui.city_search_results

import android.content.Context

/**
 * Created by Greta Radisauskaite on 09/10/2020.
 */
interface AppearanceStrategy {

    //****************************************
    // Dependencies
    //****************************************

    var context: Context

    //****************************************
    // Abstract methods
    //****************************************

    fun cityNameColor(): Int

    fun temperatureColor(): Int

    fun dateColor(): Int

    //****************************************
    // Local utils
    //****************************************

    fun getColor(resId: Int): Int {
        if (android.os.Build.VERSION.SDK_INT <= 22) {
            return context.resources.getColor(resId)
        } else {
            return context.resources.getColor(resId, null)
        }
    }
}