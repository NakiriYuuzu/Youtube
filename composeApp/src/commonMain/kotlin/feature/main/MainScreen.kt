package feature.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.QrCode2
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material.icons.twotone.WbSunny
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.domain.model.Users
import core.presentation.component.YuuzuDialog
import core.presentation.component.YuuzuSheetScaffold
import core.util.common.fastSumByFloat
import core.util.common.toDate
import core.util.utility.ObserveAsEvents
import core.util.utility.toStringResource
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import youtube.composeapp.generated.resources.Res
import youtube.composeapp.generated.resources.qr_code

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen(
    state: MainState,
    viewModel: MainViewModel
) {
    val scope = rememberCoroutineScope()
    val windowSizeClass = calculateWindowSizeClass()
    val scaffoldState = rememberBottomSheetScaffoldState()

    val showModal = remember { mutableStateOf(false) }

    val contentPadding = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 8.dp
        WindowWidthSizeClass.Medium -> 16.dp
        WindowWidthSizeClass.Expanded -> 24.dp
        else -> 16.dp
    }

    ObserveAsEvents(flow = viewModel.events) { event ->
        when(event) {
            is MainEvent.Error -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = getString(event.error.toStringResource()),
                        duration = SnackbarDuration.Short,
                        actionLabel = "Dismiss"
                    )
                }
            }
        }
    }

    YuuzuDialog(
        openDialog = showModal,
        content = {
            Text(
                text = "Payment QR Code",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(Res.drawable.qr_code),
                contentDescription = "QR Code",
                modifier = Modifier
                    .padding(contentPadding)
                    .clip(MaterialTheme.shapes.large)
            )
        },
        onConfirm = { showModal.value = false }
    )

    YuuzuSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            SheetContent(
                users = state.selectedUser,
                windowSizeClass = windowSizeClass.widthSizeClass
            )
        },
        snackbarHost = {}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(contentPadding)
        ) {
            // Top Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { viewModel.onAction(MainAction.OnThemePreferenceChange) }
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.WbSunny,
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = { showModal.value = true }
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.QrCode2,
                        contentDescription = null
                    )
                }
            }

            // Title
            Text(
                text = "Youtube, Premium!",
                style = when (windowSizeClass.widthSizeClass) {
                    WindowWidthSizeClass.Compact -> MaterialTheme.typography.headlineLarge
                    WindowWidthSizeClass.Medium -> MaterialTheme.typography.displayMedium
                    else -> MaterialTheme.typography.displayLarge
                },
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = contentPadding)
            )

            // Database Tables
            DatabaseTablesScreen(
                state = state,
                windowSizeClass = windowSizeClass.widthSizeClass,
                onViewClicked = { user ->
                    scope.launch {
                        viewModel.onAction(MainAction.OnUserClick(user))
                        scaffoldState.bottomSheetState.expand()
                    }
                }
            )
        }
    }
}

@Composable
private fun SheetContent(
    users: Users?,
    windowSizeClass: WindowWidthSizeClass
) {
    val contentPadding = when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> 8.dp
        WindowWidthSizeClass.Medium -> 16.dp
        else -> 24.dp
    }

    Column(
        modifier = Modifier.fillMaxHeight(0.9f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(contentPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左側：使用者名稱和交易記錄
            Column {
                Text(
                    text = users?.name ?: "",
                    style = when (windowSizeClass) {
                        WindowWidthSizeClass.Compact -> MaterialTheme.typography.titleMedium
                        else -> MaterialTheme.typography.titleLarge
                    }
                )
                Text(
                    text = "Transactions",
                    style = when (windowSizeClass) {
                        WindowWidthSizeClass.Compact -> MaterialTheme.typography.bodySmall
                        else -> MaterialTheme.typography.bodyMedium
                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // 右側：總金額
            val totalAmount = users?.transactions?.fastSumByFloat { it.amount } ?: 0f
            Text(
                text = "$${totalAmount}",
                style = when (windowSizeClass) {
                    WindowWidthSizeClass.Compact -> MaterialTheme.typography.titleMedium
                    else -> MaterialTheme.typography.titleLarge
                },
                color = if (totalAmount >= 0) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                }
            )
        }

        val transactions = users?.transactions ?: emptyList()
        val lazyListState = rememberLazyListState()

        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(vertical = contentPadding),
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            items(transactions) { transaction ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(contentPadding)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // 左側：筆記和日期
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = transaction.note,
                                style = when (windowSizeClass) {
                                    WindowWidthSizeClass.Compact -> MaterialTheme.typography.bodyMedium
                                    else -> MaterialTheme.typography.bodyLarge
                                },
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = transaction.createdAt.toDate(),
                                style = when (windowSizeClass) {
                                    WindowWidthSizeClass.Compact -> MaterialTheme.typography.bodySmall
                                    else -> MaterialTheme.typography.bodyMedium
                                },
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.width(contentPadding))

                        // 右側：金額
                        Text(
                            text = "$${transaction.amount}",
                            style = when (windowSizeClass) {
                                WindowWidthSizeClass.Compact -> MaterialTheme.typography.titleMedium
                                else -> MaterialTheme.typography.titleLarge
                            },
                            color = if (transaction.amount >= 0) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.error
                            }
                        )
                    }
                }

                if (transaction != transactions.last()) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = contentPadding),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun DatabaseTablesScreen(
    state: MainState,
    windowSizeClass: WindowWidthSizeClass,
    onViewClicked: (value: Users) -> Unit
) {
    val contentPadding = when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> 8.dp
        WindowWidthSizeClass.Medium -> 16.dp
        else -> 24.dp
    }

    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.33f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
        ) {
            TableHeader(windowSizeClass = windowSizeClass)

            val lazyListState = rememberLazyListState()

            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.users) { user ->
                    TableRow(
                        user = user,
                        windowSizeClass = windowSizeClass,
                        onViewClicked = onViewClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun TableHeader(windowSizeClass: WindowWidthSizeClass) {
    val contentPadding = when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> 8.dp
        WindowWidthSizeClass.Medium -> 16.dp
        else -> 24.dp
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = contentPadding/2, horizontal = contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        listOf("Name", "Amount", "Last Updated", "Actions").forEachIndexed { index, title ->
            Text(
                text = title,
                style = when (windowSizeClass) {
                    WindowWidthSizeClass.Compact -> MaterialTheme.typography.bodyMedium
                    else -> MaterialTheme.typography.bodyLarge
                },
                modifier = Modifier.weight(if (index == 2) 2f else 1f),
            )
        }
    }
}

@Composable
private fun TableRow(
    user: Users,
    windowSizeClass: WindowWidthSizeClass,
    onViewClicked: (value: Users) -> Unit
) {
    val contentPadding = when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> 8.dp
        WindowWidthSizeClass.Medium -> 16.dp
        else -> 24.dp
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = contentPadding/2, horizontal = contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val lastUpdated = user.transactions.firstOrNull()?.createdAt ?: "N/A"

        Text(
            text = user.name,
            style = when (windowSizeClass) {
                WindowWidthSizeClass.Compact -> MaterialTheme.typography.bodyMedium
                else -> MaterialTheme.typography.bodyLarge
            },
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "$${user.transactions.fastSumByFloat { it.amount }}",
            style = when (windowSizeClass) {
                WindowWidthSizeClass.Compact -> MaterialTheme.typography.bodyMedium
                else -> MaterialTheme.typography.bodyLarge
            },
            modifier = Modifier.weight(1f)
        )
        Text(
            text = lastUpdated.toDate(),
            style = when (windowSizeClass) {
                WindowWidthSizeClass.Compact -> MaterialTheme.typography.bodyMedium
                else -> MaterialTheme.typography.bodyLarge
            },
            modifier = Modifier.weight(2f)
        )
        Button(
            onClick = { onViewClicked(user) },
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.TwoTone.Search, contentDescription = null)
        }
    }
}