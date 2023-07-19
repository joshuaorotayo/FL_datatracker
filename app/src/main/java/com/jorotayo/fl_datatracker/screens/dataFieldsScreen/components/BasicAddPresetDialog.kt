package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.R.string.Add_preset_dialog_icon
import com.jorotayo.fl_datatracker.R.string.addPresetBtn
import com.jorotayo.fl_datatracker.R.string.addPresetHeader
import com.jorotayo.fl_datatracker.R.string.cancelText
import com.jorotayo.fl_datatracker.R.string.enterPresetPlaceholder
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.darkSurfaceHeadingColour
import com.jorotayo.fl_datatracker.ui.theme.lightSurfaceHeadingColour
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.regular
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.ofMaxLength

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewBasicAddPresetDialog() {
    FL_DatatrackerTheme {
        BasicAddPresetDialog(
            modifier = Modifier,
            addPreset = {},
            toggleAddPresetDialog = {}
        )
    }
}


@Composable
fun BasicAddPresetDialog(
    modifier: Modifier,
    addPreset: (String) -> Unit,
    toggleAddPresetDialog: () -> Unit
) {
    val (presetText, setText) = remember { mutableStateOf(TextFieldValue("")) }
    val maxChar = 30

    val headerColour =
        if (isSystemInDarkTheme()) darkSurfaceHeadingColour else lightSurfaceHeadingColour

    Card(
        modifier = modifier
            .padding(small)
            .defaultMinSize(minWidth = 280.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(medium),
        elevation = xSmall
    ) {
        Column(
            modifier
                .background(colors.surface)
                .padding(regular),
            horizontalAlignment = CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = small),
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = xxSmall),
                    imageVector = Icons.Default.AddBox,
                    contentDescription = stringResource(id = Add_preset_dialog_icon),
                    tint = colors.primary
                )
                Text(
                    modifier = Modifier
                        .wrapContentWidth(),
                    text = stringResource(addPresetHeader),
                    textAlign = TextAlign.Center,
                    style = typography.h5,
                    color = headerColour
                )
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = typography.h6,
                value = presetText,
                maxLines = 1,
                placeholder = {
                    Text(
                        modifier = Modifier
                            .wrapContentHeight(),
                        text = stringResource(enterPresetPlaceholder),
                        style = typography.h6,
                        color = colors.onSurface,
                        textAlign = TextAlign.Center
                    )
                },
                onValueChange = {
                    setText(it.ofMaxLength(maxLength = maxChar))
                },
                colors = textFieldColors(
                    backgroundColor = colors.surface,
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    disabledIndicatorColor = Transparent,
                    textColor = colors.onSurface
                )
            )
            Text(
                text = "${presetText.text.length} / $maxChar",
                textAlign = TextAlign.End,
                style = typography.caption,
                color = colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .background(Transparent)
            )
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick =
                    toggleAddPresetDialog
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = xxSmall),
                        text = stringResource(cancelText),
                        color = colors.primary,
                    )
                }
                TextButton(
                    enabled = presetText.text.isNotBlank(),
                    onClick = {

                        addPreset(presetText.text)
                    }) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(addPresetBtn),
                        fontWeight = ExtraBold,
                        color = headerColour
                    )
                }
            }
        }
    }
}