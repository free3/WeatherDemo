package lt.viskasnemokamai.weatherapp.dao

import androidx.room.*
import lt.viskasnemokamai.weatherapp.domain.CitySearchHistoryItem

/**
 * Created by Greta Radisauskaite on 10/10/2020.
 */
@Dao
interface CityWeatherHistoryDao {

    @Query("SELECT * FROM CitySearchHistory ORDER BY requestedDate DESC")
    fun getHistory(): List<CitySearchHistoryItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHistory(citySearchHistoryItem: CitySearchHistoryItem)

    @Query("DELETE FROM CitySearchHistory WHERE requestedDate IN (SELECT requestedDate FROM CitySearchHistory ORDER BY requestedDate DESC LIMIT -1 OFFSET :limit)")
    fun deleteHistoryPreserveLimit(limit: Int)

    @Transaction
    fun addHistoryPreserveLimit(citySearchHistoryItem: CitySearchHistoryItem, limit: Int) {
        addHistory(citySearchHistoryItem)
        deleteHistoryPreserveLimit(limit)
    }
}