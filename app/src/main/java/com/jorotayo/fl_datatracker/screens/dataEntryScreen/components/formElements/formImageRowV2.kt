package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.util.DefaultPreviews

@DefaultPreviews
@Composable
fun PreviewFormImageRowV2() {
    val dataRow = DataRowState(
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
    AppTheme {
        formImageRowV2(data = dataRow)
    }
}

@Composable
fun formImageRowV2(
    data: DataRowState,
): String {
    val maxChar = 50
    val (text, setText) = remember { mutableStateOf(TextFieldValue(data.dataItem.dataValue)) }
    val imageChanged = remember { mutableStateOf(false) }
    val currentImage = remember { mutableStateOf(Icons.Default.Headphones) }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .padding(horizontal = dimens.small)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(start = dimens.small, end = dimens.small, top = dimens.xSmall)
                .fillMaxWidth(),
            text = data.dataItem.fieldName,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.onBackground,
        )
        AnimatedVisibility(visible = !imageChanged.value) {
            Image(
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = dimens.xSmall),
                imageVector = Icons.Default.Image,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.background),
                contentDescription = "Select Image Placeholder"
            )
        }
        AnimatedVisibility(visible = imageChanged.value) {
            Image(
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = dimens.xSmall),
                imageVector = currentImage.value,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary),
                contentDescription = "Select Image Placeholder"
            )
        }

        Button(
            modifier = Modifier.padding(horizontal = dimens.small),
            onClick = {
                launcher.launch("camera/*")
            }
        ) {
            Text(text = "Add image")
        }

        Spacer(modifier = Modifier.height(12.dp))

        imageUri?.let {
            val source = ImageDecoder
                .createSource(context.contentResolver, it)
            bitmap.value = ImageDecoder.decodeBitmap(source)

            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )
            }
        }

    /*    val launcher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.getResultCode() === RESULT_OK) {
                    val uri: Uri = result.getData()?.getData()!!
                    // Use the uri to load the image
                } else if (result.getResultCode() === ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                }
            }*/
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )

    return text.text
}
