package core.data.repository

import core.data.source.YoutubeSource
import core.domain.model.Users
import core.domain.repository.YoutubeRepository
import core.presentation.util.ThemePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class YoutubeRepositoryImpl(
    private val localSource: YoutubeSource.Local,
    private val remoteSource: YoutubeSource.Remote
) : YoutubeRepository {
    override suspend fun getUsers(): List<Users> = remoteSource.getUsers()

    override suspend fun getThemePreference(): ThemePreference = localSource.getThemePreference()

    override suspend fun setThemePreference(isDarkTheme: Int) = localSource.setThemePreference(isDarkTheme)
}