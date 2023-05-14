package blblblbl.simplelife.weather.di.database.main

import blblblbl.simplelife.database.model.Hour as DBHour
import blblblbl.simplelife.forecast.data.model.forecast.Hour as MainHour

import blblblbl.simplelife.database.model.Astro as DBAstro
import blblblbl.simplelife.forecast.data.model.forecast.Astro as MainAstro


import blblblbl.simplelife.database.model.Day as DBDay
import blblblbl.simplelife.forecast.data.model.forecast.Day as MainDay

import blblblbl.simplelife.database.model.Forecastday as DBForecastday
import blblblbl.simplelife.forecast.data.model.forecast.Forecastday as MainForecastday

import blblblbl.simplelife.database.model.Forecast as DBForecast
import blblblbl.simplelife.forecast.data.model.forecast.Forecast as MainForecast

import blblblbl.simplelife.database.model.Condition as DBCondition
import blblblbl.simplelife.forecast.data.model.forecast.Condition as MainCondition

import blblblbl.simplelife.database.model.Current as DBCurrent
import blblblbl.simplelife.forecast.data.model.forecast.Current as MainCurrent

import blblblbl.simplelife.database.model.Location as DBLocation
import blblblbl.simplelife.forecast.data.model.forecast.Location as MainLocation

import blblblbl.simplelife.database.model.ForecastResponse as DBForecastResponse
import blblblbl.simplelife.forecast.data.model.forecast.ForecastResponse as MainForecastResponse


//toMain
fun DBHour.mapToMain(): MainHour {
    val MainHour = MainHour(
        timeEpoch,
        time,
        tempC,
        tempF,
        isDay,
        condition?.mapToMain(),
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
    return MainHour
}

fun DBAstro.mapToMain(): MainAstro {
    val MainAstro = MainAstro(
        sunrise, sunset, moonrise, moonset, moonPhase, moonIllumination
    )
    return MainAstro
}

fun DBDay.mapToMain(): MainDay {
    val MainDay = MainDay(
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
        condition?.mapToMain(),
        uv
    )
    return MainDay
}

fun DBForecastday.mapToMain(): MainForecastday {
    val MainForecastday = MainForecastday(
        date,
        dateEpoch,
        day?.mapToMain(),
        astro?.mapToMain(),
        ArrayList(hour.map { it.mapToMain() })
    )
    return MainForecastday
}

fun DBForecast.mapToMain(): MainForecast {
    val MainForeCast = MainForecast(
        ArrayList(forecastday.map { it.mapToMain() })
    )
    return MainForeCast
}

fun DBCondition.mapToMain(): MainCondition {
    val MainCondition = MainCondition(
        text, icon, code
    )
    return MainCondition
}

fun DBCurrent.mapToMain(): MainCurrent {
    val MainCurrent = MainCurrent(
        lastUpdatedEpoch,
        lastUpdated,
        tempC,
        tempF,
        isDay,
        condition?.mapToMain(),
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
    return MainCurrent
}

fun DBLocation.mapToMain(): MainLocation {
    val MainLocation = MainLocation(
        name, region, country, lat, lon, tzId, localtimeEpoch, localtime
    )
    return MainLocation
}

fun DBForecastResponse.mapToMain(): MainForecastResponse{
    val MainForecastResponse = MainForecastResponse(
        location?.mapToMain(),
        current?.mapToMain(),
        forecast?.mapToMain()
    )
    return MainForecastResponse
}

//toDB
fun MainHour.mapToDB(): DBHour {
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

fun MainAstro.mapToDB(): DBAstro {
    val DBAstro = DBAstro(
        sunrise, sunset, moonrise, moonset, moonPhase, moonIllumination
    )
    return DBAstro
}

fun MainDay.mapToDB(): DBDay {
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

fun MainForecastday.mapToDB(): DBForecastday {
    val DBForecastday = DBForecastday(
        date,
        dateEpoch,
        day?.mapToDB(),
        astro?.mapToDB(),
        ArrayList(hour.map { it.mapToDB() })
    )
    return DBForecastday
}

fun MainForecast.mapToDB(): DBForecast {
    val DBForeCast = DBForecast(
        ArrayList(forecastday.map { it.mapToDB() })
    )
    return DBForeCast
}

fun MainCondition.mapToDB(): DBCondition {
    val DBCondition = DBCondition(
        text, icon, code
    )
    return DBCondition
}

fun MainCurrent.mapToDB(): DBCurrent {
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

fun MainLocation.mapToDB(): DBLocation {
    val DBLocation = DBLocation(
        name, region, country, lat, lon, tzId, localtimeEpoch, localtime
    )
    return DBLocation
}

fun MainForecastResponse.mapToDB(): DBForecastResponse{
    val DBForecastResponse = DBForecastResponse(
        location?.mapToDB(),
        current?.mapToDB(),
        forecast?.mapToDB()
    )
    return DBForecastResponse
}