package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jorotayo.fl_datatracker.R

@Preview
@Composable
fun PreviewFormImageDialog() {
    FormImageDialog(
        onDismiss = {},
        modifier = Modifier
    )

}

@Composable
fun FormImageDialog(
    onDismiss: () -> Unit,
    modifier: Modifier,
) {
    val contextForToast = LocalContext.current.applicationContext

    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.image_selection_dialog_header)
                    )
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = stringResource(id = R.string.description_image_selection_icon)
                    )
                }
                Text(
                    text = stringResource(id = R.string.image_selection_dialog_msg)
                )
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {

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
}
