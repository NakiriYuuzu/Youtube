package feature.main

import core.domain.model.Users

sealed interface MainEvent {
    data class OnDetailClicked(val user: Users): MainEvent
    data class OnThemePreferenceChanged(val mode: Int): MainEvent
}