package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.xSmall

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Night Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "Light Mode")
@Composable
fun PreviewNoDataForm() {
    FL_DatatrackerTheme {
        NoDataForm()
    }
}

@Composable
fun NoDataForm() {
    /*  Box(
          modifier = Modifier
              .wrapContentSize()
              .padding(vertical = 20.dp, horizontal = 16.dp)
              .clip(shape = RoundedCornerShape(20.dp))
      )
      {*/

    //empty Message
    Card(
        modifier = Modifier
            .wrapContentSize(),
        elevation = xSmall,
        shape = RoundedCornerShape(xSmall)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(MaterialTheme.colors.surface)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier.size(128.dp),
                imageVector = Icons.Default.AddBox,
                tint = MaterialTheme.colors.primary,
                contentDescription = stringResource(id = R.string.no_data_fields_msg_icon),
            )
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.no_data_form_msg),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp),
                text = stringResource(id = R.string.no_data_fields_msg_data_form),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                color = Color.DarkGray
            )
        }
    }
}