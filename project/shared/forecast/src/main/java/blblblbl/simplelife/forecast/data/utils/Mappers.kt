package blblblbl.simplelife.forecast.data.utils

import blblblbl.simplelife.forecast.data.model.Hour as DataHour
import blblblbl.simplelife.forecast.domain.model.Hour as DomainHour

import blblblbl.simplelife.forecast.data.model.Astro as DataAstro
import blblblbl.simplelife.forecast.domain.model.Astro as DomainAstro


import blblblbl.simplelife.forecast.data.model.Day as DataDay
import blblblbl.simplelife.forecast.domain.model.Day as DomainDay

import blblblbl.simplelife.forecast.data.model.Forecastday as DataForecastday
import blblblbl.simplelife.forecast.domain.model.Forecastday as DomainForecastday

import blblblbl.simplelife.forecast.data.model.Forecast as DataForecast
import blblblbl.simplelife.forecast.domain.model.Forecast as DomainForecast

import blblblbl.simplelife.forecast.data.model.Condition as DataCondition
import blblblbl.simplelife.forecast.domain.model.Condition as DomainCondition

import blblblbl.simplelife.forecast.data.model.Current as DataCurrent
import blblblbl.simplelife.forecast.domain.model.Current as DomainCurrent

import blblblbl.simplelife.forecast.data.model.Location as DataLocation
import blblblbl.simplelife.forecast.domain.model.Location as DomainLocation

import blblblbl.simplelife.forecast.data.model.ForecastResponse as DataForecastResponse
import blblblbl.simplelife.forecast.domain.model.ForecastResponse as DomainForecastResponse

//toDomain
fun DataHour.mapToDomain(): DomainHour {
    val domainHour = DomainHour(
        timeEpoch,
        time,
        tempC,
        tempF,
        isDay,
        condition?.mapToDomain(),
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
    return domainHour
}

fun DataAstro.mapToDomain(): DomainAstro {
    val domainAstro = DomainAstro(
        sunrise, sunset, moonrise, moonset, moonPhase, moonIllumination
    )
    return domainAstro
}

fun DataDay.mapToDomain(): DomainDay {
    val domainDay = DomainDay(
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
        condition?.mapToDomain(),
        uv
    )
    return domainDay
}

fun DataForecastday.mapToDomain(): DomainForecastday {
    val domainForecastday = DomainForecastday(
        date,
        dateEpoch,
        day?.mapToDomain(),
        astro?.mapToDomain(),
        ArrayList(hour.map { it.mapToDomain() })
    )
    return domainForecastday
}

fun DataForecast.mapToDomain(): DomainForecast {
    val domainForeCast = DomainForecast(
        ArrayList(forecastday.map { it.mapToDomain() })
    )
    return domainForeCast
}

fun DataCondition.mapToDomain(): DomainCondition {
    val domainCondition = DomainCondition(
        text, icon, code
    )
    return domainCondition
}

fun DataCurrent.mapToDomain(): DomainCurrent {
    val domainCurrent = DomainCurrent(
        lastUpdatedEpoch,
        lastUpdated,
        tempC,
        tempF,
        isDay,
        condition?.mapToDomain(),
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
    return domainCurrent
}

fun DataLocation.mapToDomain(): DomainLocation {
    val domainLocation = DomainLocation(
        name, region, country, lat, lon, tzId, localtimeEpoch, localtime
    )
    return domainLocation
}

fun DataForecastResponse.mapToDomain(): DomainForecastResponse{
    val domainForecastResponse = DomainForecastResponse(
        location?.mapToDomain(),
        current?.mapToDomain(),
        forecast?.mapToDomain()
    )
    return domainForecastResponse
}

//toData

fun DomainHour.mapToData(): DataHour {
    val DataHour = DataHour(
        timeEpoch,
        time,
        tempC,
        tempF,
        isDay,
        condition?.mapToData(),
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
    return DataHour
}

fun DomainAstro.mapToData(): DataAstro {
    val DataAstro = DataAstro(
        sunrise, sunset, moonrise, moonset, moonPhase, moonIllumination
    )
    return DataAstro
}

fun DomainDay.mapToData(): DataDay {
    val DataDay = DataDay(
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
        condition?.mapToData(),
        uv
    )
    return DataDay
}

fun DomainForecastday.mapToData(): DataForecastday {
    val DataForecastday = DataForecastday(
        date,
        dateEpoch,
        day?.mapToData(),
        astro?.mapToData(),
        ArrayList(hour.map { it.mapToData() })
    )
    return DataForecastday
}

fun DomainForecast.mapToData(): DataForecast {
    val DataForeCast = DataForecast(
        ArrayList(forecastday.map { it.mapToData() })
    )
    return DataForeCast
}

fun DomainCondition.mapToData(): DataCondition {
    val DataCondition = DataCondition(
        text, icon, code
    )
    return DataCondition
}

fun DomainCurrent.mapToData(): DataCurrent {
    val DataCurrent = DataCurrent(
        lastUpdatedEpoch,
        lastUpdated,
        tempC,
        tempF,
        isDay,
        condition?.mapToData(),
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
    return DataCurrent
}

fun DomainLocation.mapToData(): DataLocation {
    val DataLocation = DataLocation(
        name, region, country, lat, lon, tzId, localtimeEpoch, localtime
    )
    return DataLocation
}

fun DomainForecastResponse.mapToData(): DataForecastResponse{
    val DataForecastResponse = DataForecastResponse(
        location?.mapToData(),
        current?.mapToData(),
        forecast?.mapToData()
    )
    return DataForecastResponse
}