package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.DataItem
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.darkSurfaceHeadingColour
import com.jorotayo.fl_datatracker.ui.theme.lightSurfaceHeadingColour
import com.jorotayo.fl_datatracker.util.Dimen


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "Light Mode")
@Composable
fun PreviewFormImageRowV2() {
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
    FL_DatatrackerTheme {

        formImageRowV2(data = dataItem, setDataValue = {})
    }
}

@Composable
fun formImageRowV2(
    data: DataRowState,
    setDataValue: (String) -> Unit,
): String {
    val maxChar = 50
    val (text, setText) = remember { mutableStateOf(TextFieldValue(data.dataItem.dataValue)) }
    val imageChanged = remember { mutableStateOf(false) }
    val currentImage = remember { mutableStateOf(Icons.Default.Headphones) }
    val focusManager = LocalFocusManager.current
    val headerColour =
        if (isSystemInDarkTheme()) darkSurfaceHeadingColour else lightSurfaceHeadingColour

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
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
            color = headerColour,
        )
        AnimatedVisibility(visible = !imageChanged.value) {
            Image(
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = Dimen.xxSmall),
                imageVector = Icons.Default.Image,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.background),
                contentDescription = "Select Image Placeholder"
            )
        }
        AnimatedVisibility(visible = imageChanged.value) {
            Image(
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = Dimen.xxSmall),
                imageVector = currentImage.value,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary),
                contentDescription = "Select Image Placeholder"
            )
        }

        Button(
            modifier = Modifier.padding(horizontal = Dimen.small),
            onClick = {
                launcher.launch("camera/*")
            }) {
            Text(text = "Add image")
        }

        Spacer(modifier = Modifier.height(12.dp))

        imageUri?.let {
            if (android.os.Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->
                Image(bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp))
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
