package com.jorotayo.fl_datatracker.util.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.ofMaxLength
import kotlinx.coroutines.CoroutineScope

data class AlertDialogState(
    val title: String,
    val text: String,
    val onDismissRequest: () -> Unit,
    val confirmButtonLabel: String,
    val confirmButtonOnClick: () -> Unit,
    val dismissButtonLabel: String? = null,
    val dismissButtonOnClick: (() -> Unit)? = null,
    val titleTextAlign: TextAlign = TextAlign.Center,
    val dismissible: Boolean = true,
    val editField: Boolean? = false,
    val editFieldFunction: ((String) -> Unit?)? = null,
    val editFieldHint: String? = null,
    val textFieldErrorText: String? = null,
    val imageIcon: ImageVector? = null,
    val scope: CoroutineScope? = null
)

@Composable
fun AlertDialog(
    alertDialogState: AlertDialogState
) {

    val (fieldText, setText) = remember { mutableStateOf(TextFieldValue("")) }
    val maxChar = 20

    AlertDialog(
        shape = RoundedCornerShape(medium),
        onDismissRequest = alertDialogState.onDismissRequest,
        title = {
            if (hasImageIcon(alertDialogState)) {
                val icon = alertDialogState.imageIcon
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = small),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .size(medium)
                            .padding(end = xxSmall),
                        imageVector = icon!!,
                        contentDescription = "Icon for ${alertDialogState.title}",
                        tint = colors.primary
                    )
                    Text(
                        text = alertDialogState.title,
                        style = MaterialTheme.typography.h6,
                        fontWeight = SemiBold,
                        modifier = Modifier.wrapContentWidth(),
                        textAlign = alertDialogState.titleTextAlign,
                        color = colors.onBackground
                    )
                }
            } else {
                Text(
                    text = alertDialogState.title,
                    style = MaterialTheme.typography.h6,
                    fontWeight = SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = alertDialogState.titleTextAlign,
                    color = colors.onBackground
                )
            }
        },
        text = {
            Text(
                text = alertDialogState.text,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                color = colors.onBackground
            )
            if (hasEditField(alertDialogState)) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = MaterialTheme.typography.h6,
                    value = fieldText,
                    maxLines = 1,
                    placeholder = {
                        if (alertDialogState.textFieldErrorText != null) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .padding(end = xxSmall),
                                    text = alertDialogState.textFieldErrorText,
                                    style = MaterialTheme.typography.h6,
                                    color = colors.primary,
                                    textAlign = TextAlign.Center
                                )
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    tint = colors.primary,
                                    contentDescription = "Text field error"
                                )
                            }
                        } else {
                            Text(
                                modifier = Modifier
                                    .wrapContentHeight(),
                                text = stringResource(R.string.enterPresetPlaceholder),
                                style = MaterialTheme.typography.h6,
                                color = colors.onSurface,
                                textAlign = TextAlign.Center
                            )
                        }

                    },
                    onValueChange = {
                        setText(it.ofMaxLength(maxLength = maxChar))
                        if (it.text.length < maxChar) {
                            alertDialogState.editFieldFunction?.let { it1 -> it1(it.text) }
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = colors.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = colors.primary.copy(alpha = 0.5F),
                        disabledIndicatorColor = Color.Transparent,
                        textColor = colors.onSurface
                    )
                )
            }
        },
        dismissButton = {
            if (hasDismissButton(alertDialogState)) Button(
                buttonState =
                ButtonState(
                    enabled = true,
                    type = ButtonType.TERTIARY,
                    label = alertDialogState.dismissButtonLabel ?: "",
                    onClick = alertDialogState.dismissButtonOnClick!!,
                    modifier = Modifier.padding(bottom = xSmall),
                    contentColor = colors.onBackground
                )
            )
        },
        confirmButton = {
            Button(
                buttonState =
                ButtonState(
                    enabled = true,
                    type = ButtonType.TERTIARY,
                    label = alertDialogState.confirmButtonLabel,
                    onClick = alertDialogState.confirmButtonOnClick,
                    modifier = Modifier.padding(bottom = xSmall),
                    contentColor = colors.primary
                )
            )
        },
        properties = DialogProperties(
            dismissOnBackPress = alertDialogState.dismissible,
            dismissOnClickOutside = alertDialogState.dismissible
        )
    )
}

private fun hasDismissButton(alertDialogState: AlertDialogState) =
    alertDialogState.dismissButtonLabel != null && alertDialogState.dismissButtonOnClick != null

private fun hasEditField(alertDialogState: AlertDialogState) =
    alertDialogState.editFieldFunction != null

private fun hasImageIcon(alertDialogState: AlertDialogState) = alertDialogState.imageIcon != null

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun AlertDialogPreview() {
    FL_DatatrackerTheme {
        val title = "Interest rate"
        val text = "Tax-free - Interest which is paid that is exempt from tax.\n" +
                "AER stands for Annual Equivalent Rate and illustrates what the " +
                "interest rate would be if interest was paid and added each year."
        val onDismissRequest = {}
        val confirmButtonLabel = "Close"
        val confirmButtonOnClick = {}

        val state = AlertDialogState(
            title,
            text,
            onDismissRequest,
            confirmButtonLabel,
            confirmButtonOnClick
        )
        AlertDialog(state)
    }
}