package core.data.source.local

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import core.data.source.YoutubeSource
import core.presentation.util.ThemePreference
import core.util.DispatcherProvider
import kotlinx.coroutines.withContext

class YoutubeLocalSource(
    private val settings: Settings,
    private val dispatcherProvider: DispatcherProvider
) : YoutubeSource.Local {
    override suspend fun getThemePreference(): ThemePreference =
        withContext(dispatcherProvider.io) {
            ThemePreference(
                settings.getInt(DARK_MODE_KEY, ThemePreference.DarkMode.System.value)
            )
        }

    override suspend fun setThemePreference(isDarkTheme: Int) =
        withContext(dispatcherProvider.io) {
            settings[DARK_MODE_KEY] = isDarkTheme
        }


    companion object {
        private const val DARK_MODE_KEY = "DARK_MODE_KEY"
    }
}