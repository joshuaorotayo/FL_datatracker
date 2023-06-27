package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.darkSurfaceHeadingColour
import com.jorotayo.fl_datatracker.ui.theme.lightSurfaceHeadingColour
import com.jorotayo.fl_datatracker.util.Dimen.xSmall

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewNoDataField() {
    FL_DatatrackerTheme {
        NoDataField()
    }
}

@Composable
fun NoDataField(
) {
    val textColour = if(isSystemInDarkTheme()) darkSurfaceHeadingColour else lightSurfaceHeadingColour
    //empty Message
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(xSmall),
        shape = RoundedCornerShape(xSmall),
        elevation = xSmall
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .wrapContentSize()
                .padding(xSmall),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(128.dp),
                imageVector = Icons.Default.AddBox,
                tint = MaterialTheme.colors.primary,
                contentDescription = stringResource(id = R.string.no_data_fields_msg_icon),
            )
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.no_data_fields_header),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
                color = textColour
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp),
                text = stringResource(id = R.string.no_data_fields_msg),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5
            )
        }
    }
}
