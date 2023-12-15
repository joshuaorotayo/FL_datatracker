package com.jorotayo.fl_datatracker.util.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SmartButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.jorotayo.fl_datatracker.ui.DefaultDualPreview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.one
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.Dimen.zero

data class Buttons(
    val type: ButtonType,
    val label: String? = "",
    val enabled: Boolean = true,
    val onClick: () -> Unit,
    val editFieldFunction: ((String) -> Unit)? = null,
    val buttonContentDescription: String? = null,
    val iconStart: ImageVector? = null,
    val iconStartContentDesc: String? = null,
    val iconEnd: ImageVector? = null,
    val iconEndContentDesc: String? = null,
    val contentPadding: PaddingValues? = null,
    val modifier: Modifier = Modifier,
    val contentColor: Color = Yellow,
    val disabledContentColor: Color = Transparent,
    val backgroundColor: Color = Transparent,
    val disabledBackgroundColor: Color = Transparent,
    val borderColor: Color = Transparent,
    val disabledBorderColor: Color = Transparent,
    val fontWeight: FontWeight? = FontWeight.SemiBold
)

enum class ButtonType {
    PRIMARY,
    SECONDARY,
    TERTIARY
}

private const val FULLY_ROUNDED = 100

@Composable
fun Button(buttonState: Buttons) {
    when (buttonState.type) {
        ButtonType.PRIMARY -> PrimaryButton(buttonState)
        ButtonType.SECONDARY -> SecondaryButton(buttonState)
        ButtonType.TERTIARY -> TertiaryButton(buttonState)
    }
}

@DefaultDualPreview
@Composable
fun PreviewButtons() {
    FL_DatatrackerTheme {
        val primaryButtonState = Buttons(
            label = "Test Button",
            onClick = {},
            enabled = true,
            type = ButtonType.PRIMARY,
            modifier = Modifier
                .padding(horizontal = medium, vertical = xxSmall)
                .fillMaxWidth(),
            contentColor = colors.primary,
            backgroundColor = colors.background,
            borderColor = colors.primary,
            fontWeight = FontWeight.SemiBold
        )
        val secondaryButtonState = Buttons(
            label = "Test Button",
            onClick = {},
            enabled = true,
            type = ButtonType.SECONDARY,
            modifier = Modifier
                .padding(horizontal = medium, vertical = xxSmall)
                .fillMaxWidth(),
            contentColor = colors.primary,
            backgroundColor = colors.background,
            borderColor = colors.primary,
            fontWeight = FontWeight.SemiBold
        )
        val tertiaryButtonState = Buttons(
            onClick = {},
            enabled = true,
            type = ButtonType.TERTIARY,
            modifier = Modifier
                .padding(xxSmall),
            contentColor = colors.primary,
            fontWeight = FontWeight.SemiBold,
            iconStart = Icons.Filled.SmartButton
        )
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(primaryButtonState)
            Button(secondaryButtonState)
            Button(tertiaryButtonState)
        }
    }
}


/**
 * Button that uses the primary colour
 */
@Composable
fun PrimaryButton(buttonState: Buttons) {
    Button(
        onClick = buttonState.onClick,
        enabled = buttonState.enabled,
        elevation = ButtonDefaults.elevation(zero),
        shape = RoundedCornerShape(FULLY_ROUNDED),
        border = BorderStroke(
            one,
            if (buttonState.enabled) buttonState.borderColor else buttonState.disabledBorderColor
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonState.backgroundColor,
            disabledBackgroundColor = buttonState.disabledBackgroundColor,
            contentColor = buttonState.contentColor,
            disabledContentColor = buttonState.disabledContentColor
        ),
        modifier = buttonState.modifier.semantics {
            contentDescription = buttonState.buttonContentDescription ?: buttonState.label ?: ""
        },
        contentPadding = buttonState.contentPadding ?: PaddingValues(xSmall)
    ) {
        Text(
            text = buttonState.label ?: "",
            fontSize = MaterialTheme.typography.subtitle1.fontSize
        )
    }
}

/**
 * Button that uses the primary colour as an outline and surface as a background
 */
@Composable
fun SecondaryButton(buttonState: Buttons) {
    Button(
        onClick = buttonState.onClick,
        enabled = buttonState.enabled,
        elevation = ButtonDefaults.elevation(zero),
        shape = RoundedCornerShape(FULLY_ROUNDED),
        border = BorderStroke(
            one,
            if (buttonState.enabled) buttonState.borderColor else buttonState.disabledBorderColor
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colors.primary,
            disabledBackgroundColor = buttonState.disabledBackgroundColor,
            contentColor = colors.onSecondary,
            disabledContentColor = colors.onSecondary
        ),
        modifier = buttonState.modifier.semantics {
            contentDescription = buttonState.buttonContentDescription ?: buttonState.label ?: ""
        },
        contentPadding = buttonState.contentPadding ?: PaddingValues(xSmall)
    ) {
        Text(
            text = buttonState.label ?: "",
            fontSize = MaterialTheme.typography.subtitle1.fontSize
            // style = buttonState.textStyle,
        )
    }
}

/**
 * Button that uses the primary on surface colour with no rounding
 */
@Composable
fun TertiaryButton(buttonState: Buttons) {
    Button(
        onClick = buttonState.onClick,
        enabled = buttonState.enabled,
        elevation = ButtonDefaults.elevation(zero),
        shape = RoundedCornerShape(FULLY_ROUNDED),
        colors = ButtonDefaults.buttonColors(
            disabledContentColor = Transparent,
            contentColor = Transparent,
            backgroundColor = Transparent,
            disabledBackgroundColor = Transparent
        ),
        modifier = buttonState.modifier.semantics {
            contentDescription = buttonState.buttonContentDescription ?: buttonState.label ?: ""
        },
        contentPadding = buttonState.contentPadding ?: PaddingValues(xSmall)
    ) {
        buttonState.iconStart?.let {
            Icon(
                imageVector = it,
                tint = buttonState.contentColor,
                contentDescription = buttonState.buttonContentDescription

            )
        }
    }
}
