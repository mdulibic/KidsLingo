package fer.digobr.kidslingo.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val darkColorScheme = darkColors(
    primary = AppOrange,
    secondary = AppGreen,
)

private val lightColorScheme = lightColors(
    primary = AppOrange,
    secondary = AppGreen,

    background = AppGreen,
    surface = AppGreen,
    onPrimary = Color.White,
)

@Composable
fun KidsLingoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    MaterialTheme(
        colors = colorScheme,
        typography = appTypography,
        content = content
    )
}
