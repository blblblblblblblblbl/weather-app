package blblblbl.simplelife.weather.di.database.cities

import blblblbl.simplelife.database.model.Hour as DBHour
import blblblbl.simplelife.cities.data.model.Hour as CitiesHour

import blblblbl.simplelife.database.model.Astro as DBAstro
import blblblbl.simplelife.cities.data.model.Astro as CitiesAstro


import blblblbl.simplelife.database.model.Day as DBDay
import blblblbl.simplelife.cities.data.model.Day as CitiesDay

import blblblbl.simplelife.database.model.Forecastday as DBForecastday
import blblblbl.simplelife.cities.data.model.Forecastday as CitiesForecastday

import blblblbl.simplelife.database.model.Forecast as DBForecast
import blblblbl.simplelife.cities.data.model.Forecast as CitiesForecast

import blblblbl.simplelife.database.model.Condition as DBCondition
import blblblbl.simplelife.cities.data.model.Condition as CitiesCondition

import blblblbl.simplelife.database.model.Current as DBCurrent
import blblblbl.simplelife.cities.data.model.Current as CitiesCurrent

import blblblbl.simplelife.database.model.Location as DBLocation
import blblblbl.simplelife.cities.data.model.Location as CitiesLocation

import blblblbl.simplelife.database.model.ForecastResponse as DBForecastResponse
import blblblbl.simplelife.cities.data.model.ForecastResponse as CitiesForecastResponse


//toCities
fun DBHour.mapToCities(): CitiesHour {
    val CitiesHour = CitiesHour(
        timeEpoch,
        time,
        tempC,
        tempF,
        isDay,
        condition?.mapToCities(),
        windMph,
        windKph,
        windDegree,
        windDir,
        pressureMb,
        pressureIn,
        precipMm,
        precipIn,
        humidity,
        cloud,
        feelslikeC,
        feelslikeF,
        windchillC,
        windchillF,
        heatindexC,
        heatindexF,
        dewpointC,
        dewpointF,
        willItRain,
        chanceOfRain,
        willItSnow,
        chanceOfSnow,
        visKm,
        visMiles,
        gustMph,
        gustKph,
        uv
    )
    return CitiesHour
}

fun DBAstro.mapToCities(): CitiesAstro {
    val CitiesAstro = CitiesAstro(
        sunrise, sunset, moonrise, moonset, moonPhase, moonIllumination
    )
    return CitiesAstro
}

fun DBDay.mapToCities(): CitiesDay {
    val CitiesDay = CitiesDay(
        maxtempC,
        maxtempF,
        mintempC,
        mintempF,
        avgtempC,
        avgtempF,
        maxwindMph,
        maxwindKph,
        totalprecipMm,
        totalprecipIn,
        totalsnowCm,
        avgvisKm,
        avgvisMiles,
        avghumidity,
        dailyWillItRain,
        dailyChanceOfRain,
        dailyWillItSnow,
        dailyChanceOfSnow,
        condition?.mapToCities(),
        uv
    )
    return CitiesDay
}

fun DBForecastday.mapToCities(): CitiesForecastday {
    val CitiesForecastday = CitiesForecastday(
        date,
        dateEpoch,
        day?.mapToCities(),
        astro?.mapToCities(),
        ArrayList(hour.map { it.mapToCities() })
    )
    return CitiesForecastday
}

fun DBForecast.mapToCities(): CitiesForecast {
    val CitiesForeCast = CitiesForecast(
        ArrayList(forecastday.map { it.mapToCities() })
    )
    return CitiesForeCast
}

fun DBCondition.mapToCities(): CitiesCondition {
    val CitiesCondition = CitiesCondition(
        text, icon, code
    )
    return CitiesCondition
}

fun DBCurrent.mapToCities(): CitiesCurrent {
    val CitiesCurrent = CitiesCurrent(
        lastUpdatedEpoch,
        lastUpdated,
        tempC,
        tempF,
        isDay,
        condition?.mapToCities(),
        windMph,
        windKph,
        windDegree,
        windDir,
        pressureMb,
        pressureIn,
        precipMm,
        precipIn,
        humidity,
        cloud,
        feelslikeC,
        feelslikeF,
        visKm,
        visMiles,
        uv,
        gustMph,
        gustKph
    )
    return CitiesCurrent
}

fun DBLocation.mapToCities(): CitiesLocation {
    val CitiesLocation = CitiesLocation(
        name, region, country, lat, lon, tzId, localtimeEpoch, localtime
    )
    return CitiesLocation
}

fun DBForecastResponse.mapToCities(): CitiesForecastResponse{
    val CitiesForecastResponse = CitiesForecastResponse(
        location?.mapToCities(),
        current?.mapToCities(),
        forecast?.mapToCities()
    )
    return CitiesForecastResponse
}

//toDB
fun CitiesHour.mapToDB(): DBHour {
    val DBHour = DBHour(
        timeEpoch,
        time,
        tempC,
        tempF,
        isDay,
        condition?.mapToDB(),
        windMph,
        windKph,
        windDegree,
        windDir,
        pressureMb,
        pressureIn,
        precipMm,
        precipIn,
        humidity,
        cloud,
        feelslikeC,
        feelslikeF,
        windchillC,
        windchillF,
        heatindexC,
        heatindexF,
        dewpointC,
        dewpointF,
        willItRain,
        chanceOfRain,
        willItSnow,
        chanceOfSnow,
        visKm,
        visMiles,
        gustMph,
        gustKph,
        uv
    )
    return DBHour
}

fun CitiesAstro.mapToDB(): DBAstro {
    val DBAstro = DBAstro(
        sunrise, sunset, moonrise, moonset, moonPhase, moonIllumination
    )
    return DBAstro
}

fun CitiesDay.mapToDB(): DBDay {
    val DBDay = DBDay(
        maxtempC,
        maxtempF,
        mintempC,
        mintempF,
        avgtempC,
        avgtempF,
        maxwindMph,
        maxwindKph,
        totalprecipMm,
        totalprecipIn,
        totalsnowCm,
        avgvisKm,
        avgvisMiles,
        avghumidity,
        dailyWillItRain,
        dailyChanceOfRain,
        dailyWillItSnow,
        dailyChanceOfSnow,
        condition?.mapToDB(),
        uv
    )
    return DBDay
}

fun CitiesForecastday.mapToDB(): DBForecastday {
    val DBForecastday = DBForecastday(
        date,
        dateEpoch,
        day?.mapToDB(),
        astro?.mapToDB(),
        ArrayList(hour.map { it.mapToDB() })
    )
    return DBForecastday
}

fun CitiesForecast.mapToDB(): DBForecast {
    val DBForeCast = DBForecast(
        ArrayList(forecastday.map { it.mapToDB() })
    )
    return DBForeCast
}

fun CitiesCondition.mapToDB(): DBCondition {
    val DBCondition = DBCondition(
        text, icon, code
    )
    return DBCondition
}

fun CitiesCurrent.mapToDB(): DBCurrent {
    val DBCurrent = DBCurrent(
        lastUpdatedEpoch,
        lastUpdated,
        tempC,
        tempF,
        isDay,
        condition?.mapToDB(),
        windMph,
        windKph,
        windDegree,
        windDir,
        pressureMb,
        pressureIn,
        precipMm,
        precipIn,
        humidity,
        cloud,
        feelslikeC,
        feelslikeF,
        visKm,
        visMiles,
        uv,
        gustMph,
        gustKph
    )
    return DBCurrent
}

fun CitiesLocation.mapToDB(): DBLocation {
    val DBLocation = DBLocation(
        name, region, country, lat, lon, tzId, localtimeEpoch, localtime
    )
    return DBLocation
}

fun CitiesForecastResponse.mapToDB(): DBForecastResponse{
    val DBForecastResponse = DBForecastResponse(
        location?.mapToDB(),
        current?.mapToDB(),
        forecast?.mapToDB()
    )
    return DBForecastResponse
}