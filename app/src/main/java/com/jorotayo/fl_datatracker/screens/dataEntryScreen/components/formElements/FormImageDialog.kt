package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode"
)
@Composable
fun PreviewFormImageDialog() {
    FL_DatatrackerTheme {
        FormImageDialog(
            onDismiss = {}
        )
    }
}

@Composable
fun FormImageDialog(
    onDismiss: () -> Unit,
) {

    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colors.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimen.small, vertical = Dimen.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = 10.dp),
                    imageVector = Icons.Default.Image,
                    contentDescription = stringResource(id = R.string.description_image_selection_icon)
                )
                Text(
                    text = stringResource(id = R.string.image_selection_dialog_header)
                )
            }
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.image_selection_dialog_msg)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Dimen.small,
                        end = Dimen.small,
                        bottom = Dimen.xxSmall,
                        top = Dimen.xxSmall
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Button(
                    modifier = Modifier
                        .wrapContentWidth(),
                    onClick = { /*TODO*/ }) {
                    Text(
                        text = "Camera"
                    )
                    Icon(
                        modifier = Modifier.padding(start = 4.dp),
                        imageVector = Icons.Default.Camera,
                        contentDescription = stringResource(id = R.string.image_camera),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }

                Button(
                    modifier = Modifier
                        .wrapContentWidth(),
                    onClick = { /*TODO*/ }) {
                    Text(
                        text = "Gallery"
                    )
                    Icon(
                        modifier = Modifier.padding(start = 4.dp),
                        imageVector = Icons.Default.ImageSearch,
                        contentDescription = stringResource(R.string.image_gallery_select),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}
