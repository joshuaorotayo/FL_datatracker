package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.Preset
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule

@Preview
@Composable
fun PreviewDeletePresetDialog() {
    val mutableValue = remember { mutableStateOf(true) }
    DeletePresetDialog(
        modifier = Modifier,
        confirmDelete = {},
        scaffold = rememberScaffoldState(),
        state = mutableValue,
        preset = Preset(0, "Church")
    )
}

@Composable
fun DeletePresetDialog(
    modifier: Modifier,
    confirmDelete: (Preset) -> Unit,
    scaffold: ScaffoldState,
    state: MutableState<Boolean>,
    preset: Preset,
) {

    val scope = rememberCoroutineScope()

    if (state.value) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(10.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier
                    .background(MaterialTheme.colors.background),
            ) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                ) {
                    Text(
                        text = "Delete Preset: " + preset.presetName,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.h5.also { FontWeight.Bold },
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.surface
                    )
                }
                //.......................................................................

                Column(
                    modifier = Modifier
                        .padding(16.dp),
                ) {

                    Image(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.preset_dialog_icon), // decorative
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(
                            color = MaterialTheme.colors.primary
                        ),
                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth(),
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp),
                        text = stringResource(id = R.string.confirm_delete_preset),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(top = 10.dp),
                            text = stringResource(id = R.string.delete_preset_warning),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.primary,
                        )
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    TextButton(onClick = {
                        state.value = false
                    }) {
                        Text(
                            text = stringResource(id = R.string.cancelText),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.surface,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                    TextButton(onClick = {
                        state.value = false
                        scope.launch {
                            scaffold.snackbarHostState.showSnackbar(
                                message = "Deleted Preset: ${preset.presetName}",
                                actionLabel = "Restore?"
                            )
                        }
                        Timer().schedule(3000) {
                            confirmDelete(preset)
                        }
                    }) {
                        Text(
                            text = stringResource(id = R.string.deleteText),
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colors.surface,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                }
            }
        }
    }
}