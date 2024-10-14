package feature.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.WbSunny
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.presentation.component.YuuzuSheetScaffold

@Composable
fun MainScreenRoot() {
    MainScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(

) {
    YuuzuSheetScaffold(
        sheetPeekHeight = 0.dp,
        sheetContent = {
            SheetContent()
        }
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.TwoTone.WbSunny,
                    contentDescription = null,
                )
            }
            ElevatedCard(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(fraction = 0.5f)
            ) {

            }
        }
    }
}

@Composable
private fun SheetContent(

) {

}