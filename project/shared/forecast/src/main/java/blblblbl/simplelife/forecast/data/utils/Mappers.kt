package blblblbl.simplelife.forecast.data.utils

import blblblbl.simplelife.forecast.data.model.forecast.Hour as DataHour
import blblblbl.simplelife.forecast.domain.model.forecast.Hour as DomainHour

import blblblbl.simplelife.forecast.data.model.forecast.Astro as DataAstro
import blblblbl.simplelife.forecast.domain.model.forecast.Astro as DomainAstro


import blblblbl.simplelife.forecast.data.model.forecast.Day as DataDay
import blblblbl.simplelife.forecast.domain.model.forecast.Day as DomainDay

import blblblbl.simplelife.forecast.data.model.forecast.Forecastday as DataForecastday
import blblblbl.simplelife.forecast.domain.model.forecast.Forecastday as DomainForecastday

import blblblbl.simplelife.forecast.data.model.forecast.Forecast as DataForecast
import blblblbl.simplelife.forecast.domain.model.forecast.Forecast as DomainForecast

import blblblbl.simplelife.forecast.data.model.forecast.Alerts as DataAlerts
import blblblbl.simplelife.forecast.domain.model.forecast.Alerts as DomainAlerts

import blblblbl.simplelife.forecast.data.model.forecast.Alert as DataAlert
import blblblbl.simplelife.forecast.domain.model.forecast.Alert as DomainAlert

import blblblbl.simplelife.forecast.data.model.forecast.Condition as DataCondition
import blblblbl.simplelife.forecast.domain.model.forecast.Condition as DomainCondition

import blblblbl.simplelife.forecast.data.model.forecast.AirQuality as DataAirQuality
import blblblbl.simplelife.forecast.domain.model.forecast.AirQuality as DomainAirQuality

import blblblbl.simplelife.forecast.data.model.forecast.Current as DataCurrent
import blblblbl.simplelife.forecast.domain.model.forecast.Current as DomainCurrent

import blblblbl.simplelife.forecast.data.model.forecast.Location as DataLocation
import blblblbl.simplelife.forecast.domain.model.forecast.Location as DomainLocation

import blblblbl.simplelife.forecast.data.model.forecast.ForecastResponse as DataForecastResponse
import blblblbl.simplelife.forecast.domain.model.forecast.ForecastResponse as DomainForecastResponse

import blblblbl.simplelife.forecast.data.model.location.Location as DataUserLocation
import blblblbl.simplelife.forecast.domain.model.location.Location as DomainUserLocation

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
        uv,
        airQuality?.mapToDomain()
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

fun DataAlerts.mapToDomain():DomainAlerts{
    val DomainAlerts = DomainAlerts(
        alert = ArrayList(alert.map { it.mapToDomain() })
    )
    return DomainAlerts
}

fun DataAlert.mapToDomain():DomainAlert{
    val DomainAlert = DomainAlert(
        headline, msgtype, severity, urgency, areas, category, certainty, event, note, effective, expires, desc, instruction
    )
    return DomainAlert
}

fun DataCondition.mapToDomain(): DomainCondition {
    val domainCondition = DomainCondition(
        text, icon, code
    )
    return domainCondition
}
fun DataAirQuality.mapToDomain(): DomainAirQuality {
    val DomainAirQuality = DomainAirQuality(
        co, no2, o3, so2, pm2_5, pm10, usEpaIndex, gbDefraIndex
    )
    return DomainAirQuality
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
        forecast?.mapToDomain(),
        alerts?.mapToDomain()
    )
    return domainForecastResponse
}

fun DataUserLocation.mapToDomain():DomainUserLocation{
    val domainUserLocation = DomainUserLocation(
        latitude, longitude
    )
    return domainUserLocation
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
        uv,
        airQuality?.mapToData()
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
fun DomainAlerts.mapToData():DataAlerts{
    val DataAlerts = DataAlerts(
        alert = ArrayList(alert.map { it.mapToData() })
    )
    return DataAlerts
}

fun DomainAlert.mapToData():DataAlert{
    val DataAlert = DataAlert(
        headline, msgtype, severity, urgency, areas, category, certainty, event, note, effective, expires, desc, instruction
    )
    return DataAlert
}
fun DomainCondition.mapToData(): DataCondition {
    val DataCondition = DataCondition(
        text, icon, code
    )
    return DataCondition
}
fun DomainAirQuality.mapToData(): DataAirQuality {
    val DataAirQuality = DataAirQuality(
        co, no2, o3, so2, pm2_5, pm10, usEpaIndex, gbDefraIndex
    )
    return DataAirQuality
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
        forecast?.mapToData(),
        alerts?.mapToData()
    )
    return DataForecastResponse
}

fun DomainUserLocation.mapToData():DataUserLocation{
    val dataUserLocation = DataUserLocation(
        latitude, longitude
    )
    return dataUserLocation
}