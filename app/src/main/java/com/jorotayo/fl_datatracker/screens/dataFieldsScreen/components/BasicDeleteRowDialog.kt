package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import kotlinx.coroutines.launch

@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode")
@Preview(
    showBackground = true,
    name = "Light Mode"
)
@Composable
fun PreviewBasicDeleteRowDialog() {
    val mutableValue = remember { mutableStateOf(true) }
    FL_DatatrackerTheme {
        BasicDeleteRowDialog(
            modifier = Modifier,
            confirmDelete = {},
            scaffold = rememberScaffoldState(),
            state = mutableValue,
            dataField = DataField(
                dataFieldId = 0,
                fieldName = "Test Field",
                dataFieldType = DataFieldType.SHORT_TEXT.ordinal,
                presetId = 0,
                first = "",
                second = "",
                third = "",
                isEnabled = true,
                fieldHint = "Textfield content"
            )
        )
    }
}


@Composable
fun BasicDeleteRowDialog(
    modifier: Modifier,
    confirmDelete: (DataField) -> Unit,
    scaffold: ScaffoldState,
    state: MutableState<Boolean>,
    dataField: DataField,
) {

    val scope = rememberCoroutineScope()

    if (state.value) {
        Card(
            modifier = modifier
                .padding(32.dp)
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .defaultMinSize(minWidth = 280.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier
                    .background(MaterialTheme.colors.surface)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.row_delete_dialog_icon),
                        tint = MaterialTheme.colors.primary
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.delete_row_header),
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    text = stringResource(id = R.string.delete_row_body),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = 24.dp),
                    text = dataField.fieldName,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface
                )

                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                )
                {
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
                                message = "Deleted DataField: ${dataField.fieldName}",
                                actionLabel = "Restore?"
                            )
                        }
                        confirmDelete(dataField)

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