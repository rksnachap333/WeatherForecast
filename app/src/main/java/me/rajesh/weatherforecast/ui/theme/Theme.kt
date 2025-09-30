package me.rajesh.weatherforecast.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import me.rajesh.weatherforecast.ui.theme.custom_color.CardColors
import me.rajesh.weatherforecast.ui.theme.custom_color.CashFlow
import me.rajesh.weatherforecast.ui.theme.custom_color.CategoryColors
import me.rajesh.weatherforecast.ui.theme.custom_color.IconColors


private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryColor,
    secondary = DarkSecondaryColor,
    tertiary = DarkPrimaryVariant,
    background = DarkBackgroundColor,
    surface = DarkSurfaceColor,
    onPrimary = DarkOnPrimary,
    onSecondary = DarkOnSecondary,
    onTertiary = DarkOnPrimary,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSecondaryVariant,
    outline = DarkBorderColor,
    inverseOnSurface = DarkOnSurface,
    inverseSurface = DarkSurfaceColor,
    inversePrimary = DarkPrimaryColor,
    surfaceTint = DarkSurfaceColor,
    outlineVariant = DarkBorderColor,
    scrim = DarkSurfaceColor,
    primaryContainer = DarkPrimaryColor,
    secondaryContainer = DarkSecondaryColor,
    onPrimaryContainer = DarkOnPrimary,
    onSecondaryContainer = DarkOnSecondary,
    tertiaryContainer = DarkSecondaryVariant,
    onTertiaryContainer = DarkOnPrimary,
    error = DarkIconDanger,
    onError = DarkOnSurface,
    errorContainer = DarkIconDanger,
    onErrorContainer = DarkOnSurface,
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimaryColor,
    secondary = LightSecondaryColor,
    tertiary = LightSecondaryVariant,
    background = LightBackgroundColor,
    surface = LightSurfaceColor,
    onPrimary = LightOnPrimary,
    onSecondary = LightOnSecondary,
    onTertiary = LightOnPrimary,
    onBackground = LightOnBackground,
    onSurface = LightOnSurface,
    surfaceVariant = LightSecondaryVariant,
    outline = LightBorderColor,
    inverseOnSurface = LightOnSurface,
    inverseSurface = LightSurfaceColor,
    inversePrimary = LightPrimaryColor,
    surfaceTint = LightSurfaceColor,
    outlineVariant = LightBorderColor,
    scrim = LightSurfaceColor,
    primaryContainer = LightPrimaryColor,
    secondaryContainer = LightSecondaryColor,
    onPrimaryContainer = LightOnPrimary,
    onSecondaryContainer = LightOnSecondary,
    tertiaryContainer = LightSecondaryVariant,
    onTertiaryContainer = LightOnPrimary,
    error = LightIconDanger,
    onError = LightOnSurface,
    errorContainer = LightIconDanger,
    onErrorContainer = LightOnSurface,

    )

// Define light & dark sets
val LightCategoryColors = CategoryColors(
    food = LightCategoryIconFood,
    transport = LightCategoryIconTransport,
    shopping = LightCategoryIconShopping,
    entertainment = LightCategoryIconEntertainment,
    health = LightCategoryIconHealth,
    education = LightCategoryIconEducation,
    travel = LightCategoryIconTravel,
    utilities = LightCategoryIconUtilities,
    staff = LightCategoryIconStaff,
)

val DarkCategoryColors = CategoryColors(
    food = DarkCategoryIconFood,
    transport = DarkCategoryIconTransport,
    shopping = DarkCategoryIconShopping,
    entertainment = DarkCategoryIconEntertainment,
    health = DarkCategoryIconHealth,
    education = DarkCategoryIconEducation,
    travel = DarkCategoryIconTravel,
    utilities = DarkCategoryIconUtilities,
    staff = DarkCategoryIconStaff,
)

val LightIconColors = IconColors(
    primary = LightIconPrimary,
    secondary = LightIconSecondary,
    success = LightIconSuccess,
    danger = LightIconDanger,
    warning = LightIconWarning,
    muted = LightIconMuted,
    accent = LightIconAccent,
    neutral = LightIconNeutral
)

val DarkIconColors = IconColors(
    primary = DarkIconPrimary,
    secondary = DarkIconSecondary,
    success = DarkIconSuccess,
    danger = DarkIconDanger,
    warning = DarkIconWarning,
    muted = DarkIconMuted,
    accent = DarkIconAccent,
    neutral = DarkIconNeutral
)

val LightCardColors = CardColors(
    background = LightCardBackground,
    border = LightBorderColor,
    divider = LightDividerColor,
)

val DarkCardColors = CardColors(
    background = DarkCardBackground,
    border = DarkBorderColor,
    divider = DarkDividerColor,
)

val LightCashFlow = CashFlow(
    income = LightIconIncome,
    expense = LightIconExpense,
)

val DarkCashFlow = CashFlow(
    income = DarkIconIncome,
    expense = DarkIconExpense,
)


val LocalIconColors = staticCompositionLocalOf { LightIconColors }
val LocalCardColors = staticCompositionLocalOf { LightCardColors }
val LocalCategoryColors = staticCompositionLocalOf { LightCategoryColors }
val LocalCashFlow = staticCompositionLocalOf { LightCashFlow }

@Composable
fun WeatherForecastTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val categoryColors = if (darkTheme) DarkCategoryColors else LightCategoryColors
    val iconColors = if (darkTheme) DarkIconColors else LightIconColors
    val cardColors = if (darkTheme) DarkCardColors else LightCardColors
    val cashFlow = if (darkTheme) DarkCashFlow else LightCashFlow

    CompositionLocalProvider(
        LocalCategoryColors provides categoryColors,
        LocalIconColors provides iconColors,
        LocalCardColors provides cardColors,
        LocalCashFlow provides cashFlow
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}