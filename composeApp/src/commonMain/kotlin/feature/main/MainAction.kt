package feature.main

import core.domain.model.Users

sealed interface MainAction {
    data class OnUserClick(val user: Users): MainAction
    data object OnThemePreferenceChange: MainAction
}