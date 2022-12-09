package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.screens.homeScreen.HomeScreenState
import com.jorotayo.fl_datatracker.viewModels.HomeScreenViewModel

@Preview(showBackground = false)
@Composable
fun PreviewSearchBar() {
    SearchBar(
        viewModel = hiltViewModel(),
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
    viewModel: HomeScreenViewModel,
    searchState: HomeScreenState
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(MaterialTheme.colors.background),
            textStyle = MaterialTheme.typography.body1,
            value = searchState.text,
            maxLines = 1,
            placeholder = {
                Text(
                    modifier = Modifier,
                    text = searchState.hint,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onPrimary
                )
            },
            onValueChange = {
                viewModel.onEvent(HomeScreenEvent.SearchItemEntered(it))
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
                        viewModel.onEvent(HomeScreenEvent.ToggleSearchBar)
                    },
                ) {
                    Icon(
                        modifier = Modifier
                            .size(28.dp)
                            .weight(1f),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Close Search View",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        )
    }
}

