package com.jorotayo.fl_datatracker.screens.homeScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.tooling.preview.Preview
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.screens.homeScreen.HomeScreenState
import com.jorotayo.fl_datatracker.ui.theme.headingTextColour
import com.jorotayo.fl_datatracker.util.Dimen.regular
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewSearchBar() {
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

@Composable
fun SearchBar(
    onHomeEvent: (HomeScreenEvent) -> Unit,
    searchState: HomeScreenState
) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(xSmall),
        shape = RoundedCornerShape(regular),
        elevation = xSmall
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(shape = RoundedCornerShape(regular)),
                textStyle = typography.h1,
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
                    IconButton(
                        onClick = {
                            //Back arrow to close search View
                            onHomeEvent(HomeScreenEvent.ToggleSearchBar)
                        },
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(regular)
                                .padding(end = xxSmall)
                                .weight(1f),
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Close Search View",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
            )
        }
    }

}

