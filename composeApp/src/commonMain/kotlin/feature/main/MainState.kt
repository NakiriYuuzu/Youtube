package feature.main

import core.domain.model.Users
import core.presentation.util.ThemePreference

data class MainState(
    val users: List<Users> = emptyList(),
    val isLoading: Boolean = false,
    val themePreference: ThemePreference = ThemePreference()
)