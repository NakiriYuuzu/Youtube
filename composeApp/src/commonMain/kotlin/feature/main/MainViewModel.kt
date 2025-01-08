package feature.main

import androidx.compose.ui.util.fastFilter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.source.YoutubeSource
import core.logging.Logger
import core.presentation.util.ThemePreference
import core.util.utility.onError
import core.util.utility.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val logger: Logger,
    private val localSource: YoutubeSource.Local,
    private val remoteSource: YoutubeSource.Remote
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            loadThemePreference()
            loadUsers()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            MainState()
        )

    private val _events = Channel<MainEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.OnUserClick -> {
                _state.update { it.copy(
                    selectedUser = action.user
                ) }
            }
            is MainAction.OnThemePreferenceChange -> {
                onThemePreferenceChange()
            }
        }
    }

    private fun onThemePreferenceChange() {
        viewModelScope.launch {
            val mode = _state.value.themePreference.darkModeValue
            val theme = if (mode == ThemePreference.DarkMode.Light.value) {
                ThemePreference.DarkMode.Dark.value
            } else {
                ThemePreference.DarkMode.Light.value
            }

            localSource.setThemePreference(theme)
            _state.update { it.copy(
                themePreference = localSource.getThemePreference()
            ) }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true
            ) }

            remoteSource.getUsers()
                .onSuccess { users ->
                    _state.update { it.copy(
                        users = users.fastFilter { user -> user.role == 1 },
                        isLoading = false
                    ) }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(MainEvent.Error(error))
                }
        }
    }

    private fun loadThemePreference() {
        viewModelScope.launch {
            _state.update { it.copy(
                themePreference = localSource.getThemePreference()
            ) }
        }
    }
}