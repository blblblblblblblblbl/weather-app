package blblblbl.simplelife.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import blblblbl.simplelife.database.dao.CityDao
import blblblbl.simplelife.database.model.CityWeatherEntity
import blblblbl.simplelife.database.utils.Converters

@TypeConverters(Converters::class)
@Database(entities = [CityWeatherEntity::class], version = 1)
abstract class CityDatabase :RoomDatabase(){
    abstract fun cityDao():CityDao
}