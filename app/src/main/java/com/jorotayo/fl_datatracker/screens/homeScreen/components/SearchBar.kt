package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
            dataItems = listOf()
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
            .height(intrinsicSize = IntrinsicSize.Min)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier
                .weight(10f)
                .offset(y = (-8).dp),
            textStyle = MaterialTheme.typography.h6,
            value = searchState.text,
            maxLines = 1,
            label = {
                Text(
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight(),
                    text = searchState.hint,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary
                )
            },
            onValueChange = {
                viewModel.onEvent(HomeScreenEvent.SearchItemEntered(it))
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                unfocusedLabelColor = MaterialTheme.colors.background,
                focusedLabelColor = MaterialTheme.colors.background,
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
                            .size(36.dp)
                            .weight(1f),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Close Search View",
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            /*     ,
                 trailingIcon = {
                     IconButton(onClick = {
                         //Clear to reset search View
                         viewModel.onEvent(HomeScreenEvent.ResetSearchBar)
                     }) {
                         Icon(
                             modifier = Modifier
                                 .size(36.dp)
                                 .weight(1f),
                             imageVector = Icons.Default.Clear,
                             contentDescription = "Clear Search View",
                             tint = MaterialTheme.colors.primary
                         )
                     }
                 }*/
        )
    }
}

