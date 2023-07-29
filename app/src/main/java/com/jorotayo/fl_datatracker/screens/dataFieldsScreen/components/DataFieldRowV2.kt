package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.rowComponents.RowDetails
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.DataFieldEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.events.RowEvent
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.DataFieldRowState
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.exampleShortField

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(xSmall),
        shape = RoundedCornerShape(xSmall),
        elevation = small,
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
