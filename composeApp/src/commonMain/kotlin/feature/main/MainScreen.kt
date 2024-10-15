package feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.twotone.Money
import androidx.compose.material.icons.twotone.QrCode2
import androidx.compose.material.icons.twotone.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.presentation.component.YuuzuSheetScaffold
import kotlinx.coroutines.launch

@Composable
fun MainScreenRoot() {
    MainScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(

) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    YuuzuSheetScaffold(
        scaffoldState = scaffoldState,
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
            Card(
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.33f)
                ),
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(fraction = 0.75f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Yuuzu",
                    )
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.QrCode2,
                            contentDescription = null,
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Youtube, Premium!",
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                    DatabaseTablesScreen(
                        onViewClicked = {
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SheetContent(

) {

}

@Composable
fun DatabaseTablesScreen(
    onViewClicked: (value: String) -> Unit
) {
    val schemaList = listOf("transactions", "users")
    val rowsList = listOf(11, 4)
    val sizeList = listOf("32 kB", "32 kB")
    val columnsList = listOf("6 columns", "5 columns")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search and Schema Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(
                            text = "Search for a table",
                            fontSize = 14.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { /* Create new table */ },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(text = "Search")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Table Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Name",
                modifier = Modifier.weight(2f),
                fontSize = 14.sp
            )
            Text(
                text = "Note",
                modifier = Modifier.weight(2f),
                fontSize = 14.sp
            )
            Text(
                text = "Amount",
                modifier = Modifier.weight(1f),
                fontSize = 14.sp
            )
            Text(
                text = "Last Updated",
                modifier = Modifier.weight(1f),
                fontSize = 14.sp
            )
            Text(
                text = "Actions",
                modifier = Modifier.weight(1f),
                fontSize = 14.sp
            )
        }

        // Table Rows
        schemaList.forEachIndexed { index, schema ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = schema,
                    modifier = Modifier.weight(2f),
                    fontSize = 14.sp
                )
                Text(
                    text = "Youtube Premium Subscription",
                    modifier = Modifier.weight(2f),
                    fontSize = 14.sp
                )
                Text(
                    text = rowsList[index].toString(),
                    modifier = Modifier.weight(1f),
                    fontSize = 14.sp
                )
                Text(
                    text = "2024/10/08",
                    modifier = Modifier.weight(1f),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.weight(1f)
                ) {
                    Button(
                        onClick = { onViewClicked("") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "View")
                    }
                }
            }
        }
    }
}
