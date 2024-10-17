package feature.main

import core.domain.model.Users
import core.util.utility.SupabaseError

sealed interface MainEvent {
    data class Error(val error: SupabaseError): MainEvent
}