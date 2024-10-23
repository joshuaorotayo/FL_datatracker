package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.util.DefaultPreviews
import kotlinx.coroutines.launch

@DefaultPreviews
@Composable
fun PreviewBasicDeleteDataDialog() {
    val mutableValue = remember { mutableStateOf(true) }
    AppTheme {
        BasicDeleteDataDialog(
            modifier = Modifier,
            confirmDelete = {},
            scaffold = rememberScaffoldState(),
            state = mutableValue,
            data = Data(
                dataId = 0,
                name = "JES - 2022/12/10",
                lastEditedTime = "10:00 - 2022/12/12",
                createdTime = "20:00 - 2022/12/10"
            )
        )
    }
}

@Composable
fun BasicDeleteDataDialog(
    modifier: Modifier,
    confirmDelete: (Data) -> Unit,
    scaffold: ScaffoldState,
    state: MutableState<Boolean>,
    data: Data,
) {
    val scope = rememberCoroutineScope()

    if (state.value) {
        Card(
            modifier = modifier
                .padding(dimens.small)
                .defaultMinSize(minWidth = 280.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(dimens.regular),
            elevation = dimens.xxSmall
        ) {
            Column(
                modifier
                    .background(MaterialTheme.colors.surface)
                    .padding(dimens.small),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimens.small)
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = dimens.small),
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete_data_dialog),
                        tint = MaterialTheme.colors.primary
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.delete_data_header),
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(bottom = dimens.small),
                    text = stringResource(id = R.string.delete_data_body),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = dimens.small),
                    text = data.name,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface
                )
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        state.value = false
                    }) {
                        Text(
                            text = "Cancel",
                            color = MaterialTheme.colors.primary,
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
