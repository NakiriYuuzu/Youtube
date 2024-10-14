package core.presentation.theme

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.materialkolor.Contrast
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.DynamicMaterialThemeState
import com.materialkolor.MaterialKolors
import com.materialkolor.PaletteStyle
import com.materialkolor.ktx.colors
import com.materialkolor.rememberDynamicMaterialThemeState
import core.util.LocalScreenSize
import core.util.ScreenSize
import core.util.getScreenHeightDp
import core.util.getScreenWidthDp

@Immutable
data class ThemeSettings(
    val color: Color = seed,
    val isDarkMode: Boolean = true,
    val contrast: Contrast = Contrast.Default,
    val style: PaletteStyle = PaletteStyle.Content,
    val isAmoled: Boolean = true,
    val isExtendedFidelity: Boolean = false,
)

internal val LocalThemeIsDark: ProvidableCompositionLocal<State<Boolean>> = compositionLocalOf {
    error("Not initialized LocalThemeIsDark.")
}

internal val LocalDynamicThemeState = compositionLocalOf<DynamicMaterialThemeState> {
    error("Not initialized LocalDynamicThemeState.")
}

internal val LocalColors = compositionLocalOf<MaterialKolors> {
    error("Not initialized LocalColors.")
}

@Composable
fun createThemeState(
    settings: ThemeSettings
): DynamicMaterialThemeState {
    return rememberDynamicMaterialThemeState(
        seedColor = settings.color,
        isDark = settings.isDarkMode,
        style = settings.style,
        extendedFidelity = settings.isExtendedFidelity,
        contrastLevel = settings.contrast.value,
    )
}

@Composable
internal fun AppTheme(
    settings: ThemeSettings,
    animate: Boolean = true,
    content: @Composable () -> Unit
) {
    val isDarkState = remember(settings.isDarkMode) { mutableStateOf(settings.isDarkMode) }
    val dynamicThemeState = createThemeState(settings)

    CompositionLocalProvider(
        LocalDynamicThemeState provides dynamicThemeState,
        LocalThemeIsDark provides isDarkState,
        LocalScreenSize provides ScreenSize(getScreenWidthDp(), getScreenHeightDp()),
        LocalColors provides dynamicThemeState.colors
    ) {
        DynamicMaterialTheme(
            state = dynamicThemeState,
            animate = animate,
            typography = SoraTypography(),
            shapes = yuuzuShape,
        ) {
            Surface(content = content)
        }
    }
}