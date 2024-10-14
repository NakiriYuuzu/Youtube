import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.presentation.theme.AppTheme
import core.presentation.theme.ThemeSettings
import feature.main.MainScreenRoot
import feature.main.MainViewModel
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun App() {
    KoinContext {
        val viewModel = koinViewModel<MainViewModel>()
        val state = viewModel.state.collectAsStateWithLifecycle()

        AppTheme(
            settings = ThemeSettings()
        ) {
            MainScreenRoot()
        }
    }
}