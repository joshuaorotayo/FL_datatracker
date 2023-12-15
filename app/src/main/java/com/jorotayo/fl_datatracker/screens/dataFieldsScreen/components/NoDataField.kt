package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.ui.DefaultDualPreview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.darkSurfaceHeadingColour
import com.jorotayo.fl_datatracker.ui.theme.lightSurfaceHeadingColour
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall

@DefaultDualPreview
@Composable
fun PreviewNoDataField() {
    FL_DatatrackerTheme {
        NoDataField(modifier = Modifier)
    }
}

@Composable
fun NoDataField(
    modifier: Modifier
) {
    val textColour =
        if (isSystemInDarkTheme()) darkSurfaceHeadingColour else lightSurfaceHeadingColour
    // empty Message
    Card(
        modifier = modifier
            .padding(small)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(xSmall),
        elevation = xSmall
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(MaterialTheme.colors.surface)
                .padding(xSmall),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(96.dp),
                imageVector = Icons.Default.AddBox,
                tint = MaterialTheme.colors.primary,
                contentDescription = stringResource(id = R.string.no_data_fields_msg_icon),
            )
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.no_data_fields_header),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h1,
                color = textColour
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp),
                text = stringResource(id = R.string.no_data_fields_msg),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1
            )
        }
    }
}
