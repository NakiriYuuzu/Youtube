package feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.source.YoutubeSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val localSource: YoutubeSource.Local,
    private val remoteSource: YoutubeSource.Remote
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart { execute() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            MainState()
        )

    private fun execute() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

//            val users = local.getUsers()
//            val themePreference = repository.getThemePreference()
//            _state.value = _state.value.copy(
//                users = users,
//                themePreference = themePreference,
//                isLoading = false
//            )
        }
    }
}