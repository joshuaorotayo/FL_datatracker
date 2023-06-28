package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import android.content.res.Configuration
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewDataFieldRowV2() {
    FL_DatatrackerTheme {
        DataFieldRowV2()
    }
}

@Composable
fun DataFieldRowV2() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(xSmall),
        shape = RoundedCornerShape(xSmall),
        elevation = small,
    ) {

        var isRowEnabled by remember {mutableStateOf(false)}
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    if (isSystemInDarkTheme()) {
                        if (isRowEnabled) MaterialTheme.colors.surface
                        else MaterialTheme.colors.primary.copy(0.3f)
                    } else if (isRowEnabled) MaterialTheme.colors.surface
                    else MaterialTheme.colors.primary.copy(0.3f)
                )
        ) {
        }
    }
}
