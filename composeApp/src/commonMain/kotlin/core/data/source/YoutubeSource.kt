package core.data.source

import core.domain.model.Users
import core.presentation.util.ThemePreference

interface YoutubeSource {
    interface Local {
        suspend fun getThemePreference(): ThemePreference
        suspend fun setThemePreference(isDarkTheme: Int)
    }
    interface Remote {
        suspend fun getUsers(): List<Users>
    }
}