package com.jorotayo.fl_datatracker.ui.screens.dataEntry.components

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew

@DefaultPreviews
@Composable
private fun PreviewImageField() {
    FL_DatatrackerThemeNew {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ImageField(label = "Editable", imageUri = null, onImageSelected = {})
            ImageField(label = "Read-only", imageUri = null, onImageSelected = {}, enabled = false)
        }
    }
}

/**
 * An image picker field supporting gallery selection and camera capture.
 *
 * Camera access is gated behind a runtime permission request. If the user
 * denies the permission a rationale dialog is shown explaining why it is needed.
 *
 * @param enabled    When false the preview area and action buttons are
 *                   non-interactive and visually muted. Defaults to true.
 */
@Composable
fun ImageField(
    modifier: Modifier = Modifier,
    label: String,
    imageUri: Uri?,
    onImageSelected: (Uri) -> Unit,
    enabled: Boolean = true
) {
    // ── Permission state ──────────────────────────────────────────────────────
    var showPermissionRationale by remember { mutableStateOf(false) }

    // Launched after permission is granted to actually open the camera
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { _ ->
        // TODO: wire FileProvider + TakePicture contract for full-res images
    }

    // Requests CAMERA permission; opens camera on grant, shows rationale on deny
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            cameraLauncher.launch(null)
        } else {
            showPermissionRationale = true
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> if (enabled) uri?.let(onImageSelected) }

    // ── Permission rationale dialog ───────────────────────────────────────────
    if (showPermissionRationale) {
        AlertDialog(
            onDismissRequest = { showPermissionRationale = false },
            title = { Text("Camera permission needed") },
            text = {
                Text(
                    "Camera access is required to take a photo. " +
                            "Please grant the permission when prompted, or use the Gallery to select an existing image."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPermissionRationale = false
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                ) { Text("Try again") }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionRationale = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // ── UI ────────────────────────────────────────────────────────────────────
    val disabledAlpha = 0.38f

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (enabled)
                MaterialTheme.colorScheme.onSurfaceVariant
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = disabledAlpha)
        )

        // Preview area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = if (enabled)
                        MaterialTheme.colorScheme.outline
                    else
                        MaterialTheme.colorScheme.outline.copy(alpha = disabledAlpha),
                    shape = MaterialTheme.shapes.medium
                )
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .then(
                    if (enabled) Modifier.clickable { galleryLauncher.launch("image/*") }
                    else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Selected image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alpha = if (enabled) 1f else disabledAlpha
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.Image,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                            alpha = if (enabled) 1f else disabledAlpha
                        )
                    )
                    Text(
                        text = if (enabled) "Tap to select an image" else "No image selected",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                            alpha = if (enabled) 1f else disabledAlpha
                        )
                    )
                }
            }
        }

        // Action buttons — hidden when read-only
        if (enabled) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Icon(
                        Icons.Default.Image,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Gallery", style = MaterialTheme.typography.labelLarge)
                }
                OutlinedButton(
                    onClick = {
                        // Request permission first; the launcher callback handles the rest
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Icon(
                        Icons.Default.AddAPhoto,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Camera", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}