package com.jorotayo.fl_datatracker.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.runtime.remember
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
import androidx.navigation.compose.rememberNavController
import com.jorotayo.fl_datatracker.data.model.DataRecord
import com.jorotayo.fl_datatracker.navigation.NavCommand.Back
import com.jorotayo.fl_datatracker.navigation.NavCommand.ToRoute
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// =============================================================================
// PREVIEW
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

@DefaultPreviews
@Composable
fun PreviewHomeScreen() {
    FL_DatatrackerThemeNew {
        Surface(modifier = Modifier.fillMaxSize()) {  // ← add Surface + fillMaxSize
            HomeScreenView(
                state = HomeState(
                    records = sampleRecords,
                    filteredRecords = sampleRecords,
                    showDeleteDialog = true
                )
            )
        }
    }
}

@DefaultPreviews
@Composable
fun PreviewHomeScreenEmpty() {
    FL_DatatrackerThemeNew {
        Surface(modifier = Modifier.fillMaxSize()) {  // ← add Surface + fillMaxSize
            HomeScreenView(state = HomeState(showDeleteDialog = true))
        }
    }
}

// =============================================================================
// HOME SCREEN — ViewModel entry point
// =============================================================================

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val state = viewModel.state.collectAsState()

    val navController = rememberNavController()
    val navigationManager = hiltViewModel<HomeScreenViewModel>().navigationManager
    // or inject via a wrapper — see below

    LaunchedEffect(Unit) {
        navigationManager.commands.collect { command ->
            when (command) {
                is ToRoute -> navController.navigate(command.route)
                is Back -> navController.popBackStack()
            }
        }
    }


        HomeScreenView(
            state = state.value,
            onEvent = viewModel::onEvent,
        )
}

// =============================================================================
// HOME SCREEN VIEW
// =============================================================================

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreenView(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit = {},
    onNavigateToEntry: (presetId: Long) -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(state.isSearchActive) {
        if (state.isSearchActive) focusRequester.requestFocus()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        if (state.filteredRecords.isEmpty()) {
            Column(modifier = Modifier.fillMaxSize()) {
                HomeHeader(
                    state = state,
                    focusRequester = focusRequester,
                    onEvent = onEvent,
                    onKeyboardDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
                EmptyHomeContent(isFiltering = state.searchQuery.isNotBlank())
            }
        } else {
            RecordList(
                state = state,
                focusRequester = focusRequester,
                onEvent = onEvent,
                onKeyboardDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            )
        }

        ExtendedFloatingActionButton(
            onClick = { onNavigateToEntry(0L) },
            icon = { Icon(Icons.Default.Add, contentDescription = null) },
            text = { Text("New Entry", style = MaterialTheme.typography.labelLarge) },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
        )
    }
}
// =============================================================================
// RECORD LIST — self-contained LazyColumn with header as first item
// =============================================================================

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun RecordList(
    state: HomeState,
    focusRequester: FocusRequester,
    onEvent: (HomeEvent) -> Unit,
    onKeyboardDone: () -> Unit
) {
    val grouped = state.filteredRecords.groupBy { formatDateHeader(it.createdAt) }
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 100.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listState
    ) {
        // Header scrolls with the list
        item(key = "header") {
            HomeHeader(
                state = state,
                focusRequester = focusRequester,
                onEvent = onEvent,
                onKeyboardDone = onKeyboardDone
            )
        }

        grouped.forEach { (dateHeader, dayRecords) ->
            item(key = "section_$dateHeader") {
                DateSectionHeader(dateHeader)
            }
            items(dayRecords, key = { it.recordId }) { record ->
                RecordCard(
                    record = record,
                    onEdit = { onEvent(HomeEvent.SelectRecord(record)) },
                    onDelete = { onEvent(HomeEvent.DeleteRecord(record)) }
                )
            }
        }
    }
}

// =============================================================================
// HEADER — title + expandable search
// =============================================================================


@Composable
private fun HomeHeader(
    state: HomeState,
    focusRequester: FocusRequester,
    onEvent: (HomeEvent) -> Unit,
    onKeyboardDone: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .statusBarsPadding()
//            .animateContentSize(
//                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
//            )
    ) {

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
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

            Surface(
                shape = CircleShape,
                color = if (state.isSearchActive)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .size(44.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onEvent(HomeEvent.ToggleSearch) }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (state.isSearchActive) Icons.Default.Clear
                        else Icons.Default.Search,
                        contentDescription = if (state.isSearchActive) "Close search"
                        else "Open search",
                        modifier = Modifier.size(20.dp),
                        tint = if (state.isSearchActive)
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = state.isSearchActive,
            enter = expandVertically(
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(300)),
            exit = shrinkVertically(
                animationSpec = tween(250, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(200))
        ) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = { onEvent(HomeEvent.SearchQueryChanged(it)) },
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
                        if (state.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onEvent(HomeEvent.ClearSearch) }) {
                                Icon(
                                    Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { onKeyboardDone() }),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )

                if (state.searchQuery.isNotBlank()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "${state.filteredRecords.size} result${if (state.filteredRecords.size != 1) "s" else ""} for \"${state.searchQuery}\"",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
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
        Divider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

// =============================================================================
// RECORD CARD
// =============================================================================

@Composable
private fun RecordCard(
    record: DataRecord,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
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
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
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
private fun EmptyHomeContent(isFiltering: Boolean) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
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
                        imageVector = if (isFiltering) Icons.Default.Search
                        else Icons.Default.History,
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