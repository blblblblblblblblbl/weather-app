package blblblbl.simplelife.weather.di.database

import android.content.Context
import androidx.room.Room
import blblblbl.simplelife.database.CityDatabase
import blblblbl.simplelife.database.utils.Converters
import blblblbl.simplelife.database.utils.GsonParser
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseCreator@Inject constructor(
    @ApplicationContext appContext: Context
) {
    private val gson = GsonBuilder().setLenient().create()
    private val db = Room.databaseBuilder(
        appContext,
        CityDatabase::class.java,
        "CityDB"
    ).addTypeConverter(Converters(GsonParser(gson))).build()
    fun getDB(): CityDatabase = db
}