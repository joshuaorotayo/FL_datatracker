package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R.string.delete_datafield_header
import com.jorotayo.fl_datatracker.R.string.delete_row_body
import com.jorotayo.fl_datatracker.R.string.row_delete_dialog_icon
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.regular
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall

@DefaultPreviews
@Composable
fun PreviewBasicDeleteRowDialog() {
    FL_DatatrackerTheme {
        BasicDeleteRowDialog(
            modifier = Modifier,
            confirmDelete = {},
            scaffold = rememberScaffoldState(),
            dialogIsVisible = true,
            dataField = DataField(
                dataFieldId = 0,
                fieldName = "Test Field",
                dataFieldType = DataFieldType.SHORT_TEXT,
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
    dialogIsVisible: Boolean,
    dataField: DataField,
) {
    val scope = rememberCoroutineScope()

    if (dialogIsVisible) {
        Card(
            modifier = modifier
                .padding(small)
                .defaultMinSize(minWidth = 280.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(medium),
            elevation = xxSmall
        ) {
            Column(
                modifier
                    .background(colors.surface)
                    .padding(regular),
                horizontalAlignment = CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = small),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Center
                ) {
                    Icon(
                        modifier = Modifier
                            .size(medium)
                            .padding(end = xxSmall),
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = row_delete_dialog_icon),
                        tint = colors.primary
                    )
                    Text(
                        modifier = Modifier
                            .wrapContentWidth(),
                        text = String.format(
                            stringResource(delete_datafield_header),
                            dataField.fieldName
                        ),
                        textAlign = TextAlign.Center,
                        style = typography.h5,
                        color = colors.onBackground
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(bottom = 24.dp),
                    text = stringResource(id = delete_row_body),
                    textAlign = TextAlign.Center,
                    style = typography.h6,
                    color = colors.onSurface
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(small),
                    onClick = { /*TODO*/ }) {
                    Text(
                        modifier = Modifier,
                        text = "Cancel",
                        color = colors.onPrimary
                    )
                }
                Spacer(modifier = Modifier.height(xSmall))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(small),
                    onClick = { /*TODO*/ }) {
                    Text(
                        modifier = Modifier,
                        text = "Delete DataField",
                        color = colors.onPrimary
                    )
                }
            }
        }
    }
}
