@file:OptIn(ExperimentalMaterialApi::class)

package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headphones
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
                fieldName = "Short Text Row",
                fieldDescription = "Short Text row example...",
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

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewBottomActionSheet() {

}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewBottomActionOptions() {
    BottomActionOptions(
        onTakeImage = { true }
    )
}

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
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .fillMaxWidth(),
            text = data.dataItem.fieldName,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.onSurface,
        )
        AnimatedVisibility(visible = data.dataItem.dataValue.isEmpty()) {
            Image(
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = 8.dp),
                imageVector = Icons.Default.Headphones,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary),
                contentDescription = "Select Image Placeholder"
            )
        }
        AnimatedVisibility(visible = data.dataItem.dataValue.isNotBlank()) {
            Image(
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = 8.dp),
                painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = imageUri.value)
                        .build()
                ),
                contentDescription = "Select Image Placeholder"
            )
        }

        Button(
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = {
                ModalBottomSheetState(ModalBottomSheetValue.Expanded)
                showBottomSheet(ModalBottomSheetState(ModalBottomSheetValue.Expanded))
                onClick()
            },

            ) {
            Text(text = "Add image")
        }
        Spacer(modifier = Modifier.height(12.dp))
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )

    return imageUri.toString()
}