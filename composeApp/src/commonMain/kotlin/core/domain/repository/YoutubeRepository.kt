package core.domain.repository

import core.domain.model.Users
import core.presentation.util.ThemePreference
import kotlinx.coroutines.flow.Flow

interface YoutubeRepository {
    suspend fun getUsers(): List<Users>
    suspend fun getThemePreference(): ThemePreference
    suspend fun setThemePreference(isDarkTheme: Int)
}