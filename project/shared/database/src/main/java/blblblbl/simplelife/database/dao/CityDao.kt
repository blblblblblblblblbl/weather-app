package blblblbl.simplelife.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import blblblbl.simplelife.database.model.CityWeatherEntity
import blblblbl.simplelife.database.model.ForecastResponse

@Dao
interface CityDao {
    @Query("SELECT * FROM CityTable")
    fun getCitiesForecastPagingSource(): PagingSource<Int,CityWeatherEntity>

    @Query("SELECT forecast FROM CityTable WHERE name LIKE :name")
    suspend fun getCityForecast(name:String):ForecastResponse?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cityWeatherEntity: CityWeatherEntity)

    @Update(entity = CityWeatherEntity::class)
    suspend fun update(weather: CityWeatherEntity)

    @Query("DELETE FROM CityTable WHERE name LIKE :name")
    suspend fun deleteCity(name: String)

    @Query("DELETE FROM CityTable")
    suspend fun clear()
}