package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.rowComponents.RowDetails
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldRowState
import com.jorotayo.fl_datatracker.ui.DefaultDualPreview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxxSmall
import com.jorotayo.fl_datatracker.util.exampleShortField

@DefaultDualPreview
@Composable
fun PreviewDataFieldRowV2() {
    FL_DatatrackerTheme {
        DataFieldRowV2(exampleShortField, onRowEvent = {}, onDataFieldEvent = {})
    }
}

@Composable
fun DataFieldRowV2(
    currentDataField: DataField,
    onRowEvent: (RowEvent) -> Unit,
    onDataFieldEvent: (DataFieldEvent) -> Unit
) {
    val currentRowState = remember {
        mutableStateOf(
            DataFieldRowState(
                currentDataField
            )
        )
    }

    val isRowEnabled = remember { mutableStateOf(currentDataField.isEnabled) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = xSmall, vertical = xxxSmall)
            .background(
                if (isSystemInDarkTheme()) {
                    if (isRowEnabled.value) {
                        MaterialTheme.colors.surface
                    } else {
                        MaterialTheme.colors.primary.copy(0.3f)
                    }
                } else if (isRowEnabled.value) {
                    MaterialTheme.colors.surface
                } else {
                    MaterialTheme.colors.primary.copy(0.3f)
                }
            ),
        shape = RoundedCornerShape(xSmall),
        elevation = xxxSmall,
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
        ) {
            RowDetails(
                rowData = currentRowState,
                onRowEvent = onRowEvent,
                onDataFieldEvent = onDataFieldEvent
            )
        }
    }
}
