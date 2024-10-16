package core.data.source

import core.domain.model.Users
import core.presentation.util.ThemePreference
import core.util.utility.Result
import core.util.utility.SupabaseError

interface YoutubeSource {
    interface Local {
        suspend fun getThemePreference(): ThemePreference
        suspend fun setThemePreference(isDarkTheme: Int)
    }
    interface Remote {
        suspend fun getUsers(): Result<List<Users>, SupabaseError>
    }
}