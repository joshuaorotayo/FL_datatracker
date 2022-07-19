package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                .height(intrinsicSize = IntrinsicSize.Min)
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(10f)
                    .offset(y = (-8).dp),
                textStyle = MaterialTheme.typography.h6,
                value = searchFieldState.text,
                maxLines = 1,
                label = {
                    Text(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxHeight(),
                        text = searchFieldState.hint,
                        style = MaterialTheme.typography.h6
                    )
                },
                onValueChange = {
                    viewModel.onEvent(HomeScreenEvent.SearchItemEntered(it))
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedLabelColor = MaterialTheme.colors.onPrimary,
                    focusedBorderColor = MaterialTheme.colors.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colors.onPrimary,
                    focusedLabelColor = MaterialTheme.colors.onPrimary,
                ),
                leadingIcon = {
                    IconButton(
                        onClick = {
                            //Back arrow to close search View
                            viewModel.onEvent(HomeScreenEvent.ToggleSearchBarHide)
                        },
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(36.dp)
                                .weight(1f),
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Close Search View",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                },
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
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            )
        }
    }
}

