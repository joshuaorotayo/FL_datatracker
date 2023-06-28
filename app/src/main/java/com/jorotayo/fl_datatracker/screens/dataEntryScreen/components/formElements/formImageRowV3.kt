@file:OptIn(ExperimentalMaterialApi::class)

package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@OptIn(ExperimentalMaterialApi::class)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewFormImageRowV3() {
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
        formImageRowV3(
            data = dataItem,
            onClick = {},
            showBottomSheet = {
                modalBottomSheetState
            }
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun formImageRowV3(
    data: DataRowState,
    onClick: () -> Unit,
    showBottomSheet: (ModalBottomSheetState) -> Unit,
): String {

    val imageUri = remember {
        mutableStateOf(data.dataItem.dataValue.toUri())
    }

    Column(
        modifier = Modifier
            .padding(horizontal = Dimen.small)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(start = Dimen.small, end = Dimen.small, top = Dimen.xxSmall)
                .fillMaxWidth(),
            text = data.dataItem.fieldName,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.onSurface,
        )
        Log.d("formImageRowV3", data.dataItem.dataValue)
        AnimatedVisibility(visible = data.dataItem.dataValue.isBlank()) {
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
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.background),
                    contentDescription = "Select Image Placeholder"
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        ModalBottomSheetState(ModalBottomSheetValue.Expanded)
                        showBottomSheet(ModalBottomSheetState(ModalBottomSheetValue.Expanded))
                        onClick()
                    },
                )
                {
                    Text(text = "Add image")
                }
            }

        }
//        AnimatedVisibility(visible = data.dataItem.dataValue.isNotBlank()) {
//            Image(
//                modifier = Modifier
//                    .size(160.dp)
//                    .padding(bottom = Dimen.xxSmall),
//                painter = rememberAsyncImagePainter(
//                    ImageRequest
//                        .Builder(LocalContext.current)
//                        .data(data = imageUri.value)
//                        .build()
//                ),
//                contentDescription = "Select Image Placeholder"
//            )
//        }
        AnimatedVisibility(visible = data.dataItem.dataValue.isNotBlank()) {
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
                        //clear image
                    },
                )
                {
                    Text(
                        text = "-",
                        style = MaterialTheme.typography.h6
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
                        .fillMaxWidth(0.5f)
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        ModalBottomSheetState(ModalBottomSheetValue.Expanded)
                        showBottomSheet(ModalBottomSheetState(ModalBottomSheetValue.Expanded))
                        onClick()
                    },
                )
                {
                    Text(text = "Add image")
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