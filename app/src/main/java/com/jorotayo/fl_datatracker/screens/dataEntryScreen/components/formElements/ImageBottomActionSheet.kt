package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.jorotayo.fl_datatracker.BuildConfig
import com.jorotayo.fl_datatracker.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageBottomActionSheet(
    state: ModalBottomSheetState,
    scope: CoroutineScope,
    onTakeImage: (isCamera: Boolean) -> Unit,
    setDataValue: (String) -> Unit,
) {

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var latestTmpUri: Uri? = null

    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            imageUri?.let { uri ->
                setDataValue
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        setDataValue(uri.toString())
    }

    val cameraPermission = Manifest.permission.CAMERA
    val galleryPermission = Manifest.permission.READ_MEDIA_IMAGES

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("request permission", "ImageBottomActionSheet: granted ")
        } else {
            // Show dialog
            Log.d("request permission", "ImageBottomActionSheet: failed ")
        }
    }

    imageUri?.let {
        val source = ImageDecoder
            .createSource(context.contentResolver, it)
        bitmap.value = ImageDecoder.decodeBitmap(source)
        setDataValue(it.toString())
        Log.d("fl_datatracker", "ImageBottomActionSheet: $it")
    }

    ModalBottomSheetLayout(
        sheetState = state,
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetContent = {

            Column {

                BottomActionOptions { isCamera ->
                    scope.launch {
                        state.hide()
                    }
                    if (isCamera) {
                        val cameraPermissionGranted =
                            checkAndRequestCameraPermission(context, cameraPermission, launcher)
                        if (cameraPermissionGranted) {

                            getTmpFileUri(context).let { uri ->
                                latestTmpUri = uri
                                cameraLauncher.launch(uri)
                            }
                        }
                    } else {
                        val galleryPermissionGranted =
                            checkAndRequestCameraPermission(context, galleryPermission, launcher)
                        if (galleryPermissionGranted) {
                            galleryLauncher.launch("image/*")
                        }
                    }
                }
            }
        }
    ) {
    }
}

fun checkAndRequestCameraPermission(
    context: Context,
    permission: String,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
): Boolean {
    val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)

    return if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        true
    } else {
        // Request a permission
        launcher.launch(permission)
        false
    }
}

fun getTmpFileUri(context: Context): Uri {
    val tmpFile =
        File.createTempFile("tmp_image_file", ".png", context.cacheDir.absoluteFile).apply {
            createNewFile()
            deleteOnExit()
        }

    return FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
}


@Composable
fun BottomActionOptions(
    onTakeImage: (Boolean) -> Unit,
) {

    Column(
        modifier = Modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.image_selection_dialog_msg),
                color = MaterialTheme.colors.onSurface
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Button(
                    modifier = Modifier
                        .wrapContentWidth(),
                    onClick = {
                        onTakeImage(true)
                    }) {
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
                    onClick = {
                        onTakeImage(false)
                    },
                ) {
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
