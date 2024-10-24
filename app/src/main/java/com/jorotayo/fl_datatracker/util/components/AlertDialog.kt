package com.jorotayo.fl_datatracker.util.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.subtitleTextColour
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.one
import com.jorotayo.fl_datatracker.util.Dimen.optionsMaxChars
import com.jorotayo.fl_datatracker.util.Dimen.regular
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.ofMaxLength
import kotlinx.coroutines.CoroutineScope

@DefaultPreviews
@Composable
fun AlertDialogPreview() {
    FL_DatatrackerTheme {
        val title = "Interest rate"
        val text = "Tax-free - Interest which is paid that is exempt from tax.\n" +
                "AER stands for Annual Equivalent Rate and illustrates what the " +
                "interest rate would be if interest was paid and added each year."
        val onDismissRequest = {}
        val confirmButtonLabel = "Yes"
        val dismissButtonLabel = "Close"
        val confirmButtonOnClick = {}
        val dismissButtonOnClick = {}

        val state = AlertDialogState(
            title,
            text,
            onDismissRequest,
            confirmButtonLabel,
            confirmButtonOnClick,
            dismissButtonLabel,
            dismissButtonOnClick
        )
        AlertDialogLayout(state)
    }
}

data class AlertDialogState(
    var title: String,
    var body: String,
    var onDismissRequest: () -> Unit,
    var confirmButtonLabel: String,
    var confirmButtonOnClick: () -> Unit,
    var dismissButtonLabel: String? = null,
    var dismissButtonOnClick: (() -> Unit)? = null,
    var titleTextAlign: TextAlign = TextAlign.Center,
    var dismissible: Boolean = true,
    var editField: Boolean? = false,
    var editFieldFunction: ((String) -> Unit?)? = null,
    var editFieldHint: String? = null,
    var textFieldErrorText: String? = null,
    var imageIcon: ImageVector? = null,
    var scope: CoroutineScope? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogLayout(
    alertDialogState: AlertDialogState
) {
    AlertDialog(
        modifier = Modifier,
        onDismissRequest = { alertDialogState.onDismissRequest },
        properties = DialogProperties(
            dismissOnBackPress = alertDialogState.dismissible,
            dismissOnClickOutside = alertDialogState.dismissible
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            tonalElevation = 12.dp,
            color = colors.background,
            shape = RoundedCornerShape(small)
        ) {
            Column(
                modifier = Modifier.padding(xSmall),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(regular)
            ) {
                DialogTitle(alertDialogState)
                DialogBody(alertDialogState)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(medium)
                ) {
                    DismissBtn(modifier = Modifier.weight(1f), alertDialogState = alertDialogState)
                    ConfirmBtn(modifier = Modifier.weight(1f), alertDialogState = alertDialogState)
                }

            }

        }
        /*   ,properties = DialogProperties(

           dismissOnBackPress = alertDialogState.dismissible,
           dismissOnClickOutside = alertDialogState.dismissible
           )*/
    }
}

@Composable
private fun DialogTitle(alertDialogState: AlertDialogState) {
    if (hasImageIcon(alertDialogState)) {
        val icon = alertDialogState.imageIcon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = regular),
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
                modifier = Modifier.wrapContentWidth(),
                text = alertDialogState.title,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                color = colors.subtitleTextColour
            )
        }
    } else {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = alertDialogState.title,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center,
            color = colors.subtitleTextColour
        )
    }
}

@Composable
private fun DialogBody(alertDialogState: AlertDialogState) {
    Text(
        text = alertDialogState.body,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Center,
        color = colors.onBackground
    )
    if (hasEditField(alertDialogState)) {
        DialogTextField(alertDialogState = alertDialogState)
    }
}

@Composable
fun DialogTextField(alertDialogState: AlertDialogState) {
    val (fieldText, setText) = remember { mutableStateOf(TextFieldValue("")) }
    val maxChar = optionsMaxChars
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
                        text = alertDialogState.textFieldErrorText!!,
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

@Composable
private fun DismissBtn(
    modifier: Modifier,
    alertDialogState: AlertDialogState
) {
    OutlinedButton(
        modifier = modifier,
        border = BorderStroke(one, colors.primary),
        shape = RoundedCornerShape(small),
        onClick = { alertDialogState.dismissButtonOnClick?.let { it() } }) {
        Text(
            modifier = Modifier,
            text = alertDialogState.dismissButtonLabel!!,
            color = colors.onSurface
        )
    }
}

@Composable
private fun ConfirmBtn(
    modifier: Modifier,
    alertDialogState: AlertDialogState
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(small),
        onClick = { alertDialogState.confirmButtonOnClick() }) {
        Text(
            modifier = Modifier,
            text = alertDialogState.confirmButtonLabel,
            color = colors.onPrimary
        )
    }
}

private fun hasDismissButton(alertDialogState: AlertDialogState) =
    alertDialogState.dismissButtonLabel != null && alertDialogState.dismissButtonOnClick != null

private fun hasEditField(alertDialogState: AlertDialogState) =
    alertDialogState.editFieldFunction != null

private fun hasImageIcon(alertDialogState: AlertDialogState) = alertDialogState.imageIcon != null

