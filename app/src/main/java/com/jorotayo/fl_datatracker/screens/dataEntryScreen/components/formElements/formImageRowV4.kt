@file:OptIn(ExperimentalMaterialApi::class)

package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen
import com.jorotayo.fl_datatracker.util.Dimen.xSmall

@OptIn(ExperimentalMaterialApi::class)
@Preview(
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = false, name = "Light Mode")
@Composable
fun PreviewFormImageRowV4() {
    FL_DatatrackerTheme {
        val modalBottomSheetState =
            rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val dataItem = DataRowState(
            DataItem(
                presetId = 0,
                dataItemId = 0,
                fieldName = "Image Row",
                fieldDescription = "Image row example...",
                dataId = 1
            ),
            hasError = false,
            errorMsg = ""
        )
        formImageRowV4(
            data = dataItem,
            onClick = {},
            showBottomSheet =
            { modalBottomSheetState.isVisible }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun formImageRowV4(
    data: DataRowState,
    onClick: () -> Unit,
    showBottomSheet: (ModalBottomSheetState) -> Unit,
): String {
    val imageUri = remember {
        mutableStateOf(data.dataItem.dataValue.toUri())
    }

    Column(
        modifier = Modifier
            .padding(xSmall)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(start = Dimen.small, end = Dimen.small, top = Dimen.xxSmall)
                .fillMaxWidth(),
            text = data.dataItem.fieldName,
            textAlign = TextAlign.Start,
            style = typography.subtitle1,
            color = MaterialTheme.colors.onSurface,
        )
        Log.d("formImageRowV4", data.dataItem.dataValue)
        AnimatedVisibility(visible = !imageUri.value.toString().contains("content")) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(bottom = Dimen.xxSmall)
                    .fillMaxWidth(0.8f)
            ) {
                Image(
                    modifier = Modifier
                        .size(160.dp)
                        .padding(bottom = Dimen.xxSmall)
                        .align(Alignment.CenterHorizontally),
                    imageVector = Icons.Default.Image,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface),
                    contentDescription = "Select Image Placeholder"
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.CenterHorizontally),
                    onClick =
                    {
                        showBottomSheet(ModalBottomSheetState(ModalBottomSheetValue.Expanded))
                        onClick()
                    },
                ) {
                    Text(
                        text = "Add image",
                        style = typography.h2
                    )
                }
            }
        }
        AnimatedVisibility(imageUri.value.toString().contains("content", ignoreCase = true)) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(bottom = Dimen.xxSmall)
                    .fillMaxWidth(0.8f)
            ) {
                Button(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.End),
                    onClick = {
                        // clear image
                    },
                ) {
                    Text(
                        text = "-",
                        style = typography.h6
                    )
                }
                Image(
                    modifier = Modifier
                        .size(160.dp)
                        .padding(bottom = Dimen.xxSmall)
                        .align(Alignment.CenterHorizontally),
                    painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .crossfade(true)
                            .data(data = imageUri.value)
                            .build()
                    ),
                    contentDescription = "Image for ${data.dataItem.fieldName}"
                )
                Button(
                    modifier = Modifier
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        ModalBottomSheetState(ModalBottomSheetValue.Expanded)
                        showBottomSheet(ModalBottomSheetState(ModalBottomSheetValue.Expanded))
                        onClick()
                    },
                ) {
                    Text(
                        text = "Add image",
                        style = typography.h2
                    )
                }
            }
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )

    return imageUri.toString()
}
