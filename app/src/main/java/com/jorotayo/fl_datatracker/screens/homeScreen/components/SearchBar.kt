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
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.ui.theme.headingTextColour
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode

@DefaultPreviews
@Composable
fun PreviewSearchBar() {
    AppTheme {
        SearchBar(
            onHomeEvent = {},
            searchState = HomeScreenState(
                isHintVisible = true,
                hint = "Search...",
                text = "",
                isSearchVisible = false,
                dataList = listOf(),
                deletedItem = Data(
                    dataId = 0,
                    createdTime = "Yesterday",
                    lastEditedTime = "Today",
                    name = "Simple Service Test"
                )
            )
        )
    }
}

@Composable
fun SearchBar(
    onHomeEvent: (HomeScreenEvent) -> Unit,
    searchState: HomeScreenState
) {
    Row(
        modifier = Modifier
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
                modifier = Modifier
                    .size(dimens.medium)
                    .padding(start = dimens.xxSmall)
                    .weight(1f),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Close Search View",
                tint = MaterialTheme.colors.primary
            )
        }
        Card(
            modifier = Modifier
                .padding(
                    start = dimens.small,
                    top = dimens.xxSmall,
                    end = dimens.small,
                    bottom = dimens.xxSmall
                )
                .fillMaxWidth(),
            shape = RoundedCornerShape(dimens.small),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = if (isDarkMode()) dimens.xxSmall else dimens.zero
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(shape = RoundedCornerShape(dimens.regular)),
                textStyle = typography.h2,
                value = searchState.text,
                maxLines = 1,
                placeholder = {
                    Text(
                        modifier = Modifier,
                        text = searchState.hint,
                        style = typography.h2,
                        color = MaterialTheme.colors.headingTextColour
                    )
                },
                onValueChange = {
                    onHomeEvent(HomeScreenEvent.SearchItemEntered(it))
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    textColor = Color.Black
                ),
                leadingIcon = {
                }
            )

        }
    }
}
