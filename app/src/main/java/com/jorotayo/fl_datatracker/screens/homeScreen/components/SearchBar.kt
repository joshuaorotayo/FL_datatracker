package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
        isSearchVisible = true,
        searchFieldState = TextFieldState()
    )
}

@Composable
fun SearchBar(
    viewModel: HomeScreenViewModel,
    isSearchVisible: Boolean,
    searchFieldState: TextFieldState
) {
    AnimatedVisibility(visible = true) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceEvenly
        ) {

            SimpleIconButton(
                modifier = Modifier,
                icon = Icons.Default.ArrowBack,
                contentDescription = "Close Search View",
                tint = MaterialTheme.colors.onPrimary
            ) {
                //Back arrow to close search View
                viewModel.onEvent(HomeScreenEvent.ToggleSearchBar(isSearchVisible))
            }

            TextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = searchFieldState.text,
                placeholder = searchFieldState.hint,
                onValueChange = {
                    viewModel.onEvent(HomeScreenEvent.SearchItemEntered(it))
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )
            SimpleIconButton(
                modifier = Modifier,
                icon = Icons.Default.Clear,
                contentDescription = "Clear Search View",
                tint = MaterialTheme.colors.onPrimary
            ) {
                //Clears the search view
                viewModel.onEvent(HomeScreenEvent.ResetSearchBar)
            }
            /*TransparentSearchField(
                modifier = Modifier.weight(5f),
                text = searchFieldState.text,
                hint = searchFieldState.hint,
                singleLine = true,
                textStyle = TextStyle(color = MaterialTheme.colors.onPrimary, fontSize = 24.sp),
                onValueChange = {
                    viewModel.onEvent(HomeScreenEvent.SearchItemEntered(it))
                },
                onFocusChange = {
                    viewModel.onEvent(HomeScreenEvent.SearchFocusChanged(it))
                },
                isHintVisible = searchFieldState.isHintVisible,
            )*/

            /*TransparentSearchField(
                text = searchFieldState.text,
                hint = searchFieldState.hint,
                onValueChange = {
                    viewModel.onEvent(HomeScreenEvent.SearchItemEntered(it))
                },
                onFocusChange = {
                    viewModel.onEvent(HomeScreenEvent.SearchFocusChanged(it))
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.body1,
                leadingIcon = {
                    SimpleIconButton(
                        modifier = Modifier
                            .padding(end = 40.dp),
                        icon = Icons.Default.ArrowBack,
                        contentDescription = "Close Search View",
                        tint = MaterialTheme.colors.onPrimary
                    ) {
                        //Back arrow to close search View
                        viewModel.onEvent(HomeScreenEvent.ToggleSearchBar(isSearchVisible))
                    }
                },
                trailingIcon = {
                    SimpleIconButton(
                        modifier = Modifier,
                        icon = Icons.Default.Clear,
                        contentDescription = "Clear Search View",
                        tint = MaterialTheme.colors.onPrimary
                    ) {
                        //Clears the search view
                        viewModel.onEvent(HomeScreenEvent.ResetSearchBar)
                    }
                }
            )*/
        }
    }
}

