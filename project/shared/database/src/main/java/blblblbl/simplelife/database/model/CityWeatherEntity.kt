package blblblbl.simplelife.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CityTable")
data class CityWeatherEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name:String,

    @ColumnInfo(name = "forecast")
    @SerializedName("forecast")
    val forecast: ForecastResponse

)
