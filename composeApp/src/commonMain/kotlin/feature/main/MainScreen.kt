package feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.QrCode2
import androidx.compose.material.icons.twotone.WbSunny
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.presentation.component.YuuzuSheetScaffold

@Composable
fun MainScreenRoot(

) {
    MainScreen(

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(

) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState()

    YuuzuSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            SheetContent()
        }
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
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
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.WbSunny,
                            contentDescription = null,
                        )
                    }
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
                        onViewClicked = {}
                    )
                }
            }
        }
    }
}

@Composable
private fun SheetContent(

) {
    Text(
        text = "Recent Transactions",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(16.dp)
    )
    LazyColumn(contentPadding = PaddingValues(vertical = 20.dp)) {
        items(5) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Column {
                    Text(text = "a", maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Spacer(Modifier.height(4.dp))
                    Text(text = "a@a", style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}

@Composable
fun DatabaseTablesScreen(
//    state: MainState,
    onViewClicked: (value: String) -> Unit
) {
    val schemaList = listOf("transactions", "users")
    val rowsList = listOf(11, 4)

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
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "Note",
                modifier = Modifier.weight(3f),
            )
            Text(
                text = "Amount",
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "Last Updated",
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "Actions",
                modifier = Modifier.weight(1f),
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
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = "Youtube Premium Subscription",
                    modifier = Modifier.weight(3f),
                )
                Text(
                    text = rowsList[index].toString(),
                    modifier = Modifier.weight(1f),
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
