package lt.viskasnemokamai.weatherapp.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import lt.viskasnemokamai.weatherapp.domain.CitySearchHistoryItem

/**
 * Created by Greta Radisauskaite on 10/10/2020.
 */
@Database(entities = [CitySearchHistoryItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityWeatherHistoryDao() : CityWeatherHistoryDao

    companion object {

        private const val DB_NAME = "Weather.db"

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}