package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

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
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.ui.theme.headingTextColour
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.util.DefaultPreviews

@DefaultPreviews
@Composable
fun PreviewNoDataForm() {
    AppTheme {
        NoDataForm(
            modifier = Modifier
        )
    }
}

@Composable
fun NoDataForm(
    modifier: Modifier
) {
    // empty Message
    Card(
        modifier = modifier
            .padding(dimens.small)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(dimens.small),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = if (isDarkMode()) dimens.xSmall else dimens.zero
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(dimens.xSmall),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(96.dp),
                imageVector = Icons.Default.AddBox,
                tint = MaterialTheme.colors.primary,
                contentDescription = stringResource(id = R.string.no_data_fields_msg_icon),
            )
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.no_data_form_msg),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.headingTextColour
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp),
                text = stringResource(id = R.string.no_data_fields_msg_data_form),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1
            )
        }
    }
}
