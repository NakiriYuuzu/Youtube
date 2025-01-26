package core.presentation.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YuuzuSheetScaffold(
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    sheetPeekHeight: Dp = 0.dp,
    sheetMaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth,
    sheetShape: Shape = BottomSheetDefaults.ExpandedShape,
    sheetSwipeEnabled: Boolean = true,
    gradient: Boolean = true,
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit = {},
    sheetContent: @Composable ColumnScope.() -> Unit,
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    content: @Composable (PaddingValues) -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = sheetPeekHeight,
        sheetMaxWidth = sheetMaxWidth,
        sheetShape = sheetShape,
        sheetSwipeEnabled = sheetSwipeEnabled,
        modifier = modifier,
        topBar = topAppBar,
        sheetContent = sheetContent,
        snackbarHost = snackbarHost
    ) { padding ->
        if (gradient) {
            YuuzuBackground(hasToolbar = true) {
                content(padding)
            }
        } else content(padding)
    }
}