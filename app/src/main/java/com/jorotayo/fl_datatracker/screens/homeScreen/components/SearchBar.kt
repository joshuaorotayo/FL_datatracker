package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.screens.homeScreen.HomeScreenState
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.headingTextColour
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.one
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall

@DefaultPreviews
@Composable
private fun PreviewSearchBar() {
    FL_DatatrackerTheme {
        SearchBar(
            onHomeEvent = {},
            searchState =
            HomeScreenState(
                isHintVisible = true,
                hint = "Search...",
                text = "",
                isSearchVisible = false,
                dataList = listOf(),
                deletedItem =
                Data(
                    dataId = 0,
                    createdTime = "Yesterday",
                    lastEditedTime = "Today",
                    name = "Simple Service Test",
                ),
            ),
        )
    }
}

@Composable
fun SearchBar(
    onHomeEvent: (HomeScreenEvent) -> Unit,
    searchState: HomeScreenState,
) {
    Row(
        modifier =
        Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = {
                // Back arrow to close search View
                onHomeEvent(HomeScreenEvent.ToggleSearchBar)
            },
        ) {
            Icon(
                modifier =
                Modifier
                    .size(medium)
                    .padding(start = xxSmall)
                    .weight(1f),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Close Search View",
                tint = MaterialTheme.colors.primary,
            )
        }
        Card(
            modifier =
            Modifier
                .padding(xSmall)
                .fillMaxWidth(),
            shape = RoundedCornerShape(small),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = if (isDarkMode()) one else xxSmall,
        ) {
            TextField(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(shape = RoundedCornerShape(xSmall)),
                textStyle = typography.h3,
                value = searchState.text,
                maxLines = 1,
                placeholder = {
                    Text(
                        modifier = Modifier,
                        text = searchState.hint,
                        style = typography.h3,
                        color = MaterialTheme.colors.headingTextColour,
                    )
                },
                onValueChange = {
                    onHomeEvent(HomeScreenEvent.SearchItemEntered(it))
                },
                colors =
                TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    textColor = Color.Black,
                ),
                leadingIcon = {
                },
            )
        }
    }
}
