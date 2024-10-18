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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.domain.model.Users
import core.presentation.component.YuuzuSheetScaffold
import core.util.common.fastSumByFloat
import core.util.common.toDate
import core.util.utility.ObserveAsEvents
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainState,
    viewModel: MainViewModel,
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    ObserveAsEvents(flow = viewModel.events) { event ->
        when(event) {
            is MainEvent.Error -> {

            }
        }
    }

    YuuzuSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            SheetContent(users = state.selectedUser)
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
                        onClick = { viewModel.onAction(MainAction.OnThemePreferenceChange) },
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.WbSunny,
                            contentDescription = null,
                        )
                    }
                    IconButton(
                        onClick = { scope.launch { scaffoldState.bottomSheetState.expand() } }
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
                        state = state,
                        onViewClicked = { user ->
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                                viewModel.onAction(MainAction.OnUserClick(user))
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
    users: Users?
) {
    Text(
        text = "Recent Transactions",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(16.dp)
    )

    val transactions = users?.transactions ?: emptyList()

    LazyColumn(contentPadding = PaddingValues(vertical = 20.dp)) {
        items(transactions) { transaction ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "${transaction.amount}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = transaction.createdAt.toDate(),
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun DatabaseTablesScreen(
    state: MainState,
    onViewClicked: (value: Users) -> Unit
) {
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
            var searchText by remember { mutableStateOf("") }
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search for a table") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            Button(
                onClick = { /* Perform search */ },
                modifier = Modifier.padding(4.dp)
            ) {
                Text("Search")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Table Header
        TableHeader()

        // Table Content
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(state.users) { user ->
                TableRow(user, onViewClicked)
            }
        }
    }
}

@Composable
private fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        listOf("Name", "Note", "Amount", "Last Updated", "Actions").forEachIndexed { index, title ->
            Text(
                text = title,
                modifier = Modifier.weight(if (index == 1 || index == 3) 2f else 1f),
            )
        }
    }
}

@Composable
private fun TableRow(user: Users, onViewClicked: (value: Users) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val note = user.transactions.firstOrNull()?.note ?: "No transactions noted"
        val lastUpdated = user.transactions.firstOrNull()?.createdAt ?: "N/A"

        Text(text = user.name, modifier = Modifier.weight(1f))
        Text(text = note, modifier = Modifier.weight(2f))
        Text(text = "$${user.transactions.fastSumByFloat { it.amount }}", modifier = Modifier.weight(1f))
        Text(text = lastUpdated.toDate(), modifier = Modifier.weight(2f))
        Button(
            onClick = { onViewClicked(user) },
            modifier = Modifier.weight(1f)
        ) {
            Text("View")
        }
    }
}
