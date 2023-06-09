package blblblbl.simplelife.weather.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import blblblbl.simplelife.material_you_utils.scheme.Scheme
import blblblbl.simplelife.settings.domain.model.config.AppConfig
import blblblbl.simplelife.settings.domain.model.config.theme.ThemeMode
import kotlinx.coroutines.flow.StateFlow


private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)


private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

@Composable
fun WeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    configFlow: StateFlow<AppConfig?>,
    content: @Composable () -> Unit
) {
    val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val config by configFlow.collectAsState()
    val userDark = config?.themeConfig?.mode?: ThemeMode.AUTO
    val isDark =
        if (userDark==ThemeMode.NIGHT) true
        else if(userDark==ThemeMode.LIGHT) false
        else darkTheme
    var colorScheme = when {
        dynamicColor && isDark -> dynamicDarkColorScheme(LocalContext.current)
        dynamicColor && !isDark -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> DarkColors
        else -> LightColors
    }
    config?.themeConfig?.color?.let { themeColor->
        if (themeColor!= Color.Transparent.toArgb()){
            val scheme: Scheme = if (!isDark) Scheme.light(themeColor) else Scheme.dark(themeColor)
            colorScheme = ColorScheme(
                primary= Color(scheme.primary),
                onPrimary= Color(scheme.onPrimary),
                primaryContainer= Color(scheme.primaryContainer),
                onPrimaryContainer= Color(scheme.onPrimaryContainer),
                inversePrimary= Color(scheme.inversePrimary),
                secondary= Color(scheme.secondary),
                onSecondary= Color(scheme.onSecondary),
                secondaryContainer= Color(scheme.secondaryContainer),
                onSecondaryContainer= Color(scheme.onSecondaryContainer),
                tertiary= Color(scheme.tertiary),
                onTertiary= Color(scheme.onTertiary),
                tertiaryContainer= Color(scheme.tertiaryContainer),
                onTertiaryContainer= Color(scheme.onTertiaryContainer),
                background= Color(scheme.background),
                onBackground= Color(scheme.onBackground),
                surface= Color(scheme.surface),
                onSurface= Color(scheme.onSurface),
                surfaceVariant= Color(scheme.surfaceVariant),
                onSurfaceVariant= Color(scheme.onSurfaceVariant),
                surfaceTint= Color(scheme.surfaceVariant),
                inverseSurface= Color(scheme.inverseSurface),
                inverseOnSurface= Color(scheme.inverseOnSurface),
                error= Color(scheme.error),
                onError= Color(scheme.onError),
                errorContainer= Color(scheme.errorContainer),
                onErrorContainer= Color(scheme.onErrorContainer),
                outline= Color(scheme.outline),
                outlineVariant= Color(scheme.outlineVariant),
                scrim= Color(scheme.scrim),
            )
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = WeatherTypography,
        shapes = WeatherShapes,
        content = content
    )
}