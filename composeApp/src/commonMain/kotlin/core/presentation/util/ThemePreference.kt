package core.presentation.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import youtube.composeapp.generated.resources.Res
import youtube.composeapp.generated.resources.theme_dark_off
import youtube.composeapp.generated.resources.theme_dark_on
import youtube.composeapp.generated.resources.theme_follow_system

data class ThemePreference(
    val darkModeValue: Int = DarkMode.System.value
) {
    enum class DarkMode(val value: Int) {
        System(1),
        Dark(2),
        Light(3)
    }

    @Composable
    fun isDarkTheme(): Boolean {
        return when (darkModeValue) {
            DarkMode.System.value -> isSystemInDarkTheme()
            DarkMode.Dark.value -> true
            else -> false
        }
    }

    @Composable
    fun getDarkThemeDesc(): String {
        return when (darkModeValue) {
            DarkMode.System.value -> stringResource(Res.string.theme_follow_system)
            DarkMode.Dark.value -> stringResource(Res.string.theme_dark_on)
            DarkMode.Light.value -> stringResource(Res.string.theme_dark_off)
            else -> throw IllegalArgumentException("Invalid dark theme value")
        }
    }
}
