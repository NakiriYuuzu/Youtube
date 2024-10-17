package core.presentation.util

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import youtube.composeapp.generated.resources.Res
import youtube.composeapp.generated.resources.theme_dark_off
import youtube.composeapp.generated.resources.theme_dark_on

data class ThemePreference(
    val darkModeValue: Int = DarkMode.Dark.value
) {
    enum class DarkMode(val value: Int) {
        Light(0),
        Dark(1)
    }

    @Composable
    fun isDarkTheme(): Boolean {
        return when (darkModeValue) {
            DarkMode.Dark.value -> false
            DarkMode.Light.value -> true
            else -> false
        }
    }

    @Composable
    fun getDarkThemeDesc(): String {
        return when (darkModeValue) {
            DarkMode.Dark.value -> stringResource(Res.string.theme_dark_on)
            DarkMode.Light.value -> stringResource(Res.string.theme_dark_off)
            else -> throw IllegalArgumentException("Invalid dark theme value")
        }
    }
}
