package feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
//        sheetPeekHeight = 0.dp,
        sheetContent = {
            SheetContent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.WbSunny,
                        contentDescription = null,
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

        }
    }
}

@Composable
private fun SheetContent(

) {

}