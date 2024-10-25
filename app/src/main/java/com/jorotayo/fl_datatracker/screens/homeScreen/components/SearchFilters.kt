package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.ui.theme.subtitleTextColour
import com.jorotayo.fl_datatracker.util.Dimen.one
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxxSmall
import com.jorotayo.fl_datatracker.util.Dimen.zero

@DefaultPreviews
@Composable
private fun PreviewSearchFilters() {
    FL_DatatrackerTheme {
        SearchFilters()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchFilters() {
    Card(
        modifier =
        Modifier
            .padding(horizontal = xSmall, vertical = xxxSmall)
            .fillMaxWidth(),
        shape = RoundedCornerShape(small),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = if (isDarkMode()) one else xxSmall,
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = xSmall, vertical = xxxSmall),
        ) {
            Row(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = xxSmall),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.padding(start = xSmall, end = xxxSmall),
                    imageVector = Icons.Default.ManageSearch,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = "Icon for search filters",
                )
                Text(
                    text = "Enable and disable search filters",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.subtitleTextColour,
                    textAlign = TextAlign.Start,
                )
            }
            FlowRow(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = xxxSmall),
            ) {
                for (filter in filterList()) {
                    SearchFilter(filter = filter)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFilter(filter: Filters) {
    val enabled = remember { mutableStateOf(false) }

    ElevatedFilterChip(
        modifier = Modifier.padding(end = xSmall),
        selected = enabled.value,
        onClick = { enabled.value = !enabled.value },
        label = { Text(text = filter.filterName) },
        leadingIcon = {
            AnimatedVisibility(visible = enabled.value) {
                Icon(
                    imageVector = Icons.Default.Check,
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = "Checked filter for ${filter.filterName}",
                )
            }
        },
        colors =
        FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colors.primary.copy(alpha = 0.9f),
            selectedLabelColor = Color.White,
            containerColor = MaterialTheme.colors.primary.copy(alpha = 0.25f),
            labelColor = MaterialTheme.colors.subtitleTextColour,
        ),
        elevation =
        FilterChipDefaults.elevatedFilterChipElevation(
            elevation = zero,
            pressedElevation = xSmall,
        ),
    )
}

fun filterList(): List<Filters> {
    val filterList = mutableListOf<Filters>()
    filterList.add(
        Filters("Field Name"),
    )
    filterList.add(
        Filters("Field Value"),
    )
    filterList.add(
        Filters("Data value"),
    )
    filterList.add(
        Filters("Meeting Name"),
    )
    filterList.add(
        Filters("DataFieldName"),
    )
    return filterList
}
