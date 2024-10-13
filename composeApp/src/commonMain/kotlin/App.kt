import androidx.compose.runtime.*
import core.presentation.theme.AppTheme
import core.presentation.theme.ThemeSettings
import feature.main.MainScreenRoot
import org.koin.compose.KoinContext

@Composable
internal fun App() {
    KoinContext {

        AppTheme(
            settings = ThemeSettings()
        ) {
            MainScreenRoot()
        }
    }
}