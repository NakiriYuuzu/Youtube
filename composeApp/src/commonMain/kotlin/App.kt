import Youtube.composeApp.BuildConfig
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.presentation.theme.AppTheme
import core.presentation.theme.ThemeSettings
import feature.main.MainScreen
import feature.main.MainViewModel
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun App() {
    KoinContext {
        println(BuildConfig.SUPABASE_KEY)
        println(BuildConfig.SUPABASE_URL)
        val viewModel = koinViewModel<MainViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        AppTheme(
            settings = ThemeSettings(
                isDarkMode = state.themePreference.isDarkTheme()
            )
        ) {
            MainScreen(
                state = state,
                viewModel = viewModel
            )
        }
    }
}