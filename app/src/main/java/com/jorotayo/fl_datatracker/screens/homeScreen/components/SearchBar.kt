package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorotayo.fl_datatracker.viewModels.HomeScreenViewModel

@Preview(showBackground = false)
@Composable
fun PreviewSearchBar() {
    SearchBar(
        viewModel = hiltViewModel(),
        searchFieldState = TextFieldState()
    )
}

@Composable
fun SearchBar(
    viewModel: HomeScreenViewModel,
    searchFieldState: TextFieldState
) {
    AnimatedVisibility(visible = true) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f),
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.h5,
                value = searchFieldState.text,
                label = {
                    Text(searchFieldState.hint)
                },
                onValueChange = {
                    viewModel.onEvent(HomeScreenEvent.SearchItemEntered(it))
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = MaterialTheme.colors.onPrimary
                ),
                leadingIcon = {

                    IconButton(onClick = {
                        //Clear to reset search View
                        viewModel.onEvent(HomeScreenEvent.ResetSearchBar)
                    }) {
                        Icon(
                            modifier = Modifier.weight(1f),
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear Search View",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            //Back arrow to close search View
                            viewModel.onEvent(HomeScreenEvent.ToggleSearchBarHide)
                        },
                    ) {
                        Icon(
                            modifier = Modifier.weight(1f),
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Close Search View",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            )
        }
    }
}

