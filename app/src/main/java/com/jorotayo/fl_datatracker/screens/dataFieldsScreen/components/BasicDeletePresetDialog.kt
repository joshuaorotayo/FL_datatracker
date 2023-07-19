package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.darkSurfaceHeadingColour
import com.jorotayo.fl_datatracker.ui.theme.lightSurfaceHeadingColour
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.regular
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import kotlinx.coroutines.launch

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(
    showBackground = true,
    name = "Light Mode"
)
@Composable
fun PreviewBasicDeletePresetDialog() {
    FL_DatatrackerTheme {
        BasicDeletePresetDialog(
            modifier = Modifier,
            confirmDelete = {},
//            onPresetEvent = {},
            scaffold = rememberScaffoldState(),
            preset = Preset(0, "Church")
        )
    }
}


@Composable
fun BasicDeletePresetDialog(
    modifier: Modifier,
    confirmDelete: (Preset) -> Unit,
//    onPresetEvent: (TogglePresetDeleteDialog) -> Unit,
    scaffold: ScaffoldState,
    preset: Preset,
) {

    val headerColour =
        if (isSystemInDarkTheme()) darkSurfaceHeadingColour else lightSurfaceHeadingColour

    val scope = rememberCoroutineScope()
    Card(
        modifier = modifier
            .padding(small)
            .defaultMinSize(minWidth = 280.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(medium),
        elevation = xSmall
    ) {
        Column(
            modifier
                .background(MaterialTheme.colors.surface)
                .padding(regular),
            horizontalAlignment = CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = small),
                verticalAlignment = CenterVertically,
                horizontalArrangement = Center
            ){
                Icon(
                    modifier = Modifier
                        .padding(end = xxSmall),
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.preset_dialog_icon),
                    tint = MaterialTheme.colors.primary
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = String.format(
                        stringResource(R.string.deletePresetHeader),
                        preset.presetName
                    ),
                    style = MaterialTheme.typography.h5,
                    color = headerColour
                )
            }

            Text(
                modifier = Modifier
                    .padding(bottom = small),
                text = stringResource(id = R.string.confirm_delete_preset),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 24.dp),
                text = stringResource(id = R.string.delete_preset_warning),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface
            )

            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {
//                    onPresetEvent(TogglePresetDeleteDialog)
                }) {
                    Text(
                        modifier = Modifier
                            .padding(end = xxSmall),
                        text = stringResource(id = R.string.cancelText),
                        color = MaterialTheme.colors.primary
                    )
                }
                TextButton(onClick = {
                    scope.launch {
                        scaffold.snackbarHostState.showSnackbar(
                            message = "Deleted Preset: ${preset.presetName}",
                            actionLabel = "Restore?"
                        )
                    }
                    confirmDelete(preset)
                }) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.deleteText),
                        color = headerColour
                    )
                }
            }
        }
    }
}