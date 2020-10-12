package lt.viskasnemokamai.weatherapp.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Greta Radisauskaite on 08/10/2020.
 */
@Entity(tableName = "CitySearchHistory")
data class CitySearchHistoryItem(

    @PrimaryKey(autoGenerate = false)
    val name: String,

    /**
     * Time in millis
     */
    @ColumnInfo(name = "requestedDate")
    val requestedDate: Long

) : Serializable
