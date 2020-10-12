package lt.viskasnemokamai.weatherapp

import android.app.Application
import lt.viskasnemokamai.weatherapp.dao.AppDatabase


/**
 * Created by Greta Radisauskaite on 08/10/2020.
 */
open class WeatherApplication : Application() {

    var database: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()

        database = AppDatabase.getInstance(this)
    }
}