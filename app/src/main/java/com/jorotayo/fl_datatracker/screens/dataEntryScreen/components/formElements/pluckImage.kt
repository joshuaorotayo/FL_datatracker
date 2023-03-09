package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.himanshoe.pluck.PluckConfiguration
import com.himanshoe.pluck.ui.Pluck
import com.himanshoe.pluck.ui.permission.Permission

@Composable
fun pluckImage(): String {
    var imageUri by remember { mutableStateOf("") }
    Permission(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ),
        goToAppSettings = {
            // Go to App Settings
        }
    ) {
        Pluck(
            pluckConfiguration = PluckConfiguration(multipleImagesAllowed = false),
            onPhotoSelected =
            {
                imageUri = it[0].uri.toString()
            }
        )
    }
    return imageUri
}