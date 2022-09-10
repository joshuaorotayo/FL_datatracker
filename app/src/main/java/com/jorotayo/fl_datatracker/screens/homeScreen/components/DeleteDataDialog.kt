package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.Data
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewDeleteDataDialog() {
}

@Composable
fun DeleteDataDialog(
    modifier: Modifier,
    confirmDelete: (Data) -> Unit,
    scaffold: ScaffoldState,
    state: MutableState<Boolean>,
    data: Data,
) {

    val scope = rememberCoroutineScope()

    if (state.value) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier
                    .background(MaterialTheme.colors.background)
            ) {

                //.......................................................................
                Image(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Data dialog, delete icon", // decorative
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colors.primary
                    ),
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(70.dp)
                        .fillMaxWidth(),
                )

                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp, start = 25.dp, end = 25.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "'${data.name}'",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Delete Data Item",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.h6,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp),
                        text = "Are you sure you want to delete this Data Item?",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.surface),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    TextButton(onClick = {
                        state.value = false
                    }) {
                        Text(
                            text = "Cancel",
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                    TextButton(onClick = {
                        state.value = false
                        scope.launch {
                            scaffold.snackbarHostState.showSnackbar(
                                message = "Deleted Data Item: ${data.name}",
                                actionLabel = "Restore?"
                            )
                        }
                        confirmDelete(data)
                        /*Timer().schedule(3000) {
                        }*/
                    }) {
                        Text(
                            "Delete",
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                }
            }
        }
    }
}