package blblblbl.simplelife.settings.data.utils

//theme
import blblblbl.simplelife.settings.data.model.config.theme.ThemeMode as DataThemeMode
import blblblbl.simplelife.settings.domain.model.config.theme.ThemeMode as DomainThemeMode
import blblblbl.simplelife.settings.data.model.config.theme.ThemeConfig as DataThemeConfig
import blblblbl.simplelife.settings.domain.model.config.theme.ThemeConfig as DomainThemeConfig
//weather
import blblblbl.simplelife.settings.data.model.config.weather.DegreeUnit as DataDegreeUnit
import blblblbl.simplelife.settings.domain.model.config.weather.DegreeUnit as DomainDegreeUnit
import blblblbl.simplelife.settings.data.model.config.weather.SpeedUnit as DataSpeedUnit
import blblblbl.simplelife.settings.domain.model.config.weather.SpeedUnit as DomainSpeedUnit
import blblblbl.simplelife.settings.data.model.config.weather.WeatherConfig as DataWeatherConfig
import blblblbl.simplelife.settings.domain.model.config.weather.WeatherConfig as DomainWeatherConfig
//app
import blblblbl.simplelife.settings.data.model.config.AppConfig as DataAppConfig
import blblblbl.simplelife.settings.domain.model.config.AppConfig as DomainAppConfig
//widget
import blblblbl.simplelife.settings.data.model.config.widget.WidgetConfig as DataWidgetConfig
import blblblbl.simplelife.settings.domain.model.config.widget.WidgetConfig as DomainWidgetConfig


//theme
fun DataThemeMode.mapToDomain():DomainThemeMode{
    return try {
        DomainThemeMode.valueOf(this.name)
    }
    catch (e:Throwable){
        DomainThemeMode.AUTO
    }
}

fun DomainThemeMode.mapToData():DataThemeMode{
    return try {
        DataThemeMode.valueOf(this.name)
    }
    catch (e:Throwable){
        DataThemeMode.AUTO
    }
}

fun DataThemeConfig.mapToDomain():DomainThemeConfig{
    val domainThemeConfig = DomainThemeConfig(
        mode = this.mode.mapToDomain(),
        color = this.color
    )
    return domainThemeConfig
}

fun DomainThemeConfig.mapToData():DataThemeConfig{
    val DataThemeConfig = DataThemeConfig(
        mode = this.mode.mapToData(),
        color = this.color
    )
    return DataThemeConfig
}
//weather
fun DataDegreeUnit.mapToDomain():DomainDegreeUnit{
    return try {
        DomainDegreeUnit.valueOf(this.name)
    }
    catch (e:Throwable){
        DomainDegreeUnit.C
    }
}
fun DomainDegreeUnit.mapToData():DataDegreeUnit{
    return try {
        DataDegreeUnit.valueOf(this.name)
    }
    catch (e:Throwable){
        DataDegreeUnit.C
    }
}


fun DataSpeedUnit.mapToDomain():DomainSpeedUnit{
    return try {
        DomainSpeedUnit.valueOf(this.name)
    }
    catch (e:Throwable){
        DomainSpeedUnit.Ms
    }
}

fun DomainSpeedUnit.mapToData():DataSpeedUnit{
    return try {
        DataSpeedUnit.valueOf(this.name)
    }
    catch (e:Throwable){
        DataSpeedUnit.Ms
    }
}

fun DataWeatherConfig.mapToDomain():DomainWeatherConfig{
    val domainWeatherConfig = DomainWeatherConfig(
        degreeUnit = this.degreeUnit.mapToDomain(),
        speedUnit = this.speedUnit.mapToDomain()
    )
    return domainWeatherConfig
}

fun DomainWeatherConfig.mapToData():DataWeatherConfig{
    val DataWeatherConfig = DataWeatherConfig(
        degreeUnit = this.degreeUnit.mapToData(),
        speedUnit = this.speedUnit.mapToData()
    )
    return DataWeatherConfig
}

//app
fun DataAppConfig.mapToDomain():DomainAppConfig{
    val domainAppConfig = DomainAppConfig(
        themeConfig = this.themeConfig.mapToDomain(),
        weatherConfig = this.weatherConfig.mapToDomain(),
        widgetConfig = widgetConfig.mapToDomain()
    )
    return domainAppConfig
}

fun DomainAppConfig.mapToData():DataAppConfig{
    val DataAppConfig = DataAppConfig(
        themeConfig = this.themeConfig.mapToData(),
        weatherConfig = this.weatherConfig.mapToData(),
        widgetConfig = widgetConfig.mapToData()
    )
    return DataAppConfig
}
//widget
fun DataWidgetConfig.mapToDomain():DomainWidgetConfig{
    val DomainWidgetConfig = DomainWidgetConfig(
        updateTime = updateTime
    )
    return DomainWidgetConfig
}
fun DomainWidgetConfig.mapToData():DataWidgetConfig{
    val DataWidgetConfig = DataWidgetConfig(
        updateTime = updateTime
    )
    return DataWidgetConfig
}