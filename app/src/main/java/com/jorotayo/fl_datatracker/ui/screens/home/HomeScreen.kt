package com.jorotayo.fl_datatracker.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jorotayo.fl_datatracker.data.model.DataRecord
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.components.toasts.AppToastData
import com.jorotayo.fl_datatracker.ui.scaffold.SetScaffold
import com.jorotayo.fl_datatracker.ui.screens.home.components.DeleteRecordDialog
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew
import com.jorotayo.fl_datatracker.ui.util.Dimensions.spacingMedium
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// =============================================================================
// SAMPLE DATA — shared across previews
// =============================================================================

private val sampleRecords = listOf(
    DataRecord(
        recordId = 1,
        presetId = 1,
        title = "Morning Inspection",
        createdAt = System.currentTimeMillis() - 86_400_000
    ),
    DataRecord(
        recordId = 2,
        presetId = 1,
        title = "Evening Inspection",
        createdAt = System.currentTimeMillis() - 172_800_000
    ),
    DataRecord(
        recordId = 3,
        presetId = 2,
        title = "Weekly Fitness Log",
        createdAt = System.currentTimeMillis() - 259_200_000
    ),
    DataRecord(
        recordId = 4,
        presetId = 2,
        title = "Monthly Finance Review",
        createdAt = System.currentTimeMillis() - 604_800_000
    ),
)

// =============================================================================
// PREVIEWS — all use HomeScreenView directly, no ViewModel needed
// =============================================================================

@DefaultPreviews
@Composable
private fun PreviewHomeScreenWithRecords() {
    FL_DatatrackerThemeNew {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreenView(
                state = HomeScreenState(
                    records = sampleRecords,
                    filteredRecords = sampleRecords,
                    showDeleteDialog = false
                )
            )
        }
    }
}

@DefaultPreviews
@Composable
private fun PreviewHomeScreenSearchActive() {
    FL_DatatrackerThemeNew {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreenView(
                state = HomeScreenState(
                    records = sampleRecords,
                    filteredRecords = sampleRecords.take(2),
                    searchQuery = "Inspection",
                    isSearchActive = true,
                    showDeleteDialog = false
                )
            )
        }
    }
}

@DefaultPreviews
@Composable
private fun PreviewHomeScreenEmpty() {
    FL_DatatrackerThemeNew {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreenView(state = HomeScreenState(showDeleteDialog = false))
        }
    }
}

@DefaultPreviews
@Composable
private fun PreviewHomeScreenEmptyFiltered() {
    FL_DatatrackerThemeNew {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreenView(
                state = HomeScreenState(
                    records = sampleRecords,
                    filteredRecords = emptyList(),
                    searchQuery = "xyz",
                    isSearchActive = true,
                    showDeleteDialog = false
                )
            )
        }
    }
}

// =============================================================================
// HOME SCREEN — stateful entry point, owns ViewModel + scaffold config + toast
// =============================================================================

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val state by viewModel.state.collectAsState()

    var currentToast by remember { mutableStateOf<AppToastData?>(null) }
    LaunchedEffect(Unit) {
        viewModel.toastFlow.collect { toastData ->
            currentToast = null          // force recompose even if same message
            currentToast = toastData
        }
    }
    // ── Shared scaffold config ────────────────────────────────────────────────
    // Lambdas capture `state` so title subtitle and search icon visibility
    // recompose automatically as records or search state changes.
    // FAB and search icon are wired to ViewModel events directly here so
    // HomeScreenView stays free of any event knowledge beyond its onEvent param.
    SetScaffold(
        title = {
            Column {
                Text(
                    text = "Records",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (state.records.isNotEmpty()) {
                    Text(
                        text = "${state.records.size} total entries",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        actions = {
            // Hide search icon when search bar is already open so they don't
            // fight for the same space in the top bar
            if (state.records.isNotEmpty() && !state.isSearchActive) {
                IconButton(onClick = { viewModel.onEvent(HomeEvent.ToggleSearch) }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search records",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        fab = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.onEvent(HomeEvent.NavigateToEntry()) },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("New Entry", style = MaterialTheme.typography.labelLarge) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        },
        toast = currentToast
    )

    Box(modifier = Modifier.fillMaxSize()) {
        HomeScreenView(
            state = state,
            onEvent = viewModel::onEvent
        )
        /*AppToast(
            data = state.toast,
            onDismiss = { viewModel.onEvent(HomeEvent.DismissToast) }
        )*/
        if (state.showDeleteDialog) {
            DeleteRecordDialog(state = state, onEvent = viewModel::onEvent)
        }
    }
}

// =============================================================================
// HOME SCREEN VIEW — stateless and preview-safe, no ViewModel or Scaffold
// =============================================================================

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreenView(
    state: HomeScreenState,
    onEvent: (HomeEvent) -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(state.isSearchActive) {
        if (state.isSearchActive) focusRequester.requestFocus()
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // ── Search bar ────────────────────────────────────────────────────────
        // Kept here rather than in SetScaffold because it needs FocusRequester
        // and keyboard controllers which are Compose locals. Animating it as a
        // Column child below the shared TopAppBar is cleaner than replacing the
        // entire TopAppBar slot through ScaffoldViewModel.
        AnimatedVisibility(
            visible = state.isSearchActive,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            SearchBar(
                query = state.searchQuery,
                resultCount = state.filteredRecords.size,
                focusRequester = focusRequester,
                onQueryChange = { onEvent(HomeEvent.SearchQueryChanged(it)) },
                onClose = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    onEvent(HomeEvent.ToggleSearch)
                },
                onSearch = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            )
        }

        // ── Records or empty state ────────────────────────────────────────────
        if (state.filteredRecords.isEmpty()) {
            EmptyHomeContent(
                modifier = Modifier.fillMaxSize(),
                isFiltering = state.searchQuery.isNotBlank()
            )
        } else {
            RecordList(
                modifier = Modifier.fillMaxSize(),
                state = state,
                onEvent = onEvent
            )
        }
    }
}

// =============================================================================
// SEARCH BAR — shown below the shared TopAppBar when isSearchActive is true
// =============================================================================

@Composable
private fun SearchBar(
    query: String,
    resultCount: Int,
    focusRequester: FocusRequester,
    onQueryChange: (String) -> Unit,
    onClose: () -> Unit,
    onSearch: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacingMedium)
            .padding(bottom = spacingMedium)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            placeholder = {
                Text("Search records…", style = MaterialTheme.typography.bodyMedium)
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                IconButton(onClick = onClose) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Close search",
                        modifier = Modifier.size(18.dp)
                    )
                }
            },
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )
        if (query.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$resultCount result${if (resultCount != 1) "s" else ""} for \"$query\"",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// =============================================================================
// RECORD LIST
// =============================================================================

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RecordList(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    onEvent: (HomeEvent) -> Unit
) {
    val grouped = state.filteredRecords.groupBy { formatDateHeader(it.createdAt) }
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listState
    ) {
        grouped.forEach { (dateHeader, dayRecords) ->
            stickyHeader(key = "header_$dateHeader") {
                DateSectionHeader(dateHeader)
            }
            items(
                items = dayRecords,
                key = { "record_${it.recordId}" }  // globally unique, not just per-group
            ) { record ->
                RecordCard(
                    record = record,
                    onEdit = { onEvent(HomeEvent.SelectRecord(record)) },
                    onDelete = { onEvent(HomeEvent.RequestDeleteRecord(record)) }
                )
            }
        }
    }
}
// =============================================================================
// DATE SECTION HEADER
// =============================================================================

@Composable
private fun DateSectionHeader(label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        Divider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
    }
}

// =============================================================================
// RECORD CARD
// =============================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecordCard(
    record: DataRecord,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        onClick = onEdit,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.title.ifBlank { "Untitled Record" },
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = formatTime(record.createdAt),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(onClick = onEdit, modifier = Modifier.size(36.dp)) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit record",
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDelete, modifier = Modifier.size(36.dp)) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete record",
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

// =============================================================================
// EMPTY STATE
// =============================================================================

@Composable
private fun EmptyHomeContent(
    modifier: Modifier = Modifier,
    isFiltering: Boolean
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(80.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (isFiltering) Icons.Default.Search else Icons.Default.History,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Text(
                text = if (isFiltering) "No matching records" else "No records yet",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = if (isFiltering) "Try a different search term"
                else "Tap the button below to create your first entry",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// =============================================================================
// DATE HELPERS
// =============================================================================

private fun formatDateHeader(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    val sevenDaysMs = 7 * 86_400_000L
    return when {
        diff < 86_400_000L -> "Today"
        diff < 172_800_000L -> "Yesterday"
        diff < sevenDaysMs -> SimpleDateFormat("EEEE", Locale.getDefault()).format(Date(timestamp))
        else -> SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(Date(timestamp))
    }
}

private fun formatTime(timestamp: Long): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))