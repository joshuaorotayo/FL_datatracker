package com.jorotayo.fl_datatracker.util.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.jorotayo.fl_datatracker.ui.DefaultDualPreview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.one
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.Dimen.zero

data class ButtonState(
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
    val modifier: Modifier = Modifier,
    val contentColor: Color? = Color.Gray,
    val disabledContentColor: Color? = Transparent,
    val backgroundColor: Color? = Transparent,
    val disabledBackgroundColor: Color? = Transparent,
    val borderColor: Color? = Color.Black,
    val disabledBorderColor: Color? = Color.Gray
)

enum class ButtonType {
    PRIMARY,
    SECONDARY,
    TERTIARY
}

private const val FULLY_ROUNDED = 100

@Composable
fun CustomButton(buttonState: ButtonState) {
    when (buttonState.type) {
        ButtonType.PRIMARY -> PrimaryButton(buttonState)
        ButtonType.SECONDARY -> SecondaryButton(buttonState)
        ButtonType.TERTIARY -> TertiaryButton(buttonState)
    }
}

@DefaultDualPreview
@Composable
private fun PreviewButtons() {
    FL_DatatrackerTheme {
        val primaryButtonState = ButtonState(
            label = "Test Button",
            onClick = {},
            enabled = true,
            type = ButtonType.PRIMARY,
            modifier = Modifier
                .padding(horizontal = medium, vertical = xxSmall)
                .fillMaxWidth(),
            contentColor = colors.primary,
            backgroundColor = colors.background,
            borderColor = colors.primary
        )
        val secondaryButtonState = ButtonState(
            label = "Test Button",
            onClick = {},
            enabled = true,
            type = ButtonType.SECONDARY,
            modifier = Modifier
                .padding(horizontal = medium, vertical = xxSmall)
                .fillMaxWidth(),
            contentColor = colors.primary,
            backgroundColor = colors.background,
            borderColor = colors.primary
        )
        val tertiaryButtonState = ButtonState(
            onClick = {},
            enabled = true,
            type = ButtonType.TERTIARY,
            modifier = Modifier
                .padding(xxSmall),
            contentColor = colors.primary,
            iconStart = Icons.Filled.SmartButton
        )
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomButton(primaryButtonState)
            CustomButton(secondaryButtonState)
            CustomButton(tertiaryButtonState)
        }
    }
}


/**
 * Button that uses the primary colour as an outline and surface as a background
 */
@Composable
private fun PrimaryButton(buttonState: ButtonState) {
    Button(
        onClick = buttonState.onClick,
        enabled = buttonState.enabled,
        elevation = ButtonDefaults.elevation(zero),
        shape = RoundedCornerShape(FULLY_ROUNDED),
        border = BorderStroke(
            one,
            if (buttonState.enabled) colors.primary else colors.primary.copy(alpha = 0.5f)
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colors.primary,
            contentColor = colors.onSecondary,
            disabledBackgroundColor = Transparent,
            disabledContentColor = colors.onSecondary
        ),
        modifier = buttonState.modifier.semantics {
            contentDescription = buttonState.buttonContentDescription ?: buttonState.label ?: ""
        }
    ) {
        Text(
            text = buttonState.label ?: "",
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.SemiBold
            // style = buttonState.textStyle,
        )
    }
}

/**
 * Button that uses the primary colour
 */
@Composable
private fun SecondaryButton(buttonState: ButtonState) {
    Button(
        onClick = buttonState.onClick,
        enabled = buttonState.enabled,
        elevation = ButtonDefaults.elevation(zero),
        shape = RoundedCornerShape(FULLY_ROUNDED),
        border = BorderStroke(
            one,
            if (buttonState.enabled) colors.primary else colors.primary.copy(alpha = 0.5f)
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Transparent,
            contentColor = colors.primary,
        ),
        modifier = buttonState.modifier.semantics {
            contentDescription = buttonState.buttonContentDescription ?: buttonState.label ?: ""
        }
    ) {
        Text(
            text = buttonState.label ?: "",
            fontSize = MaterialTheme.typography.subtitle1.fontSize
        )
    }
}

/**
 * Button that uses the primary on surface colour with no rounding
 */
@Composable
private fun TertiaryButton(buttonState: ButtonState) {
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
        }
    ) {
        buttonState.iconStart?.let {
            Icon(
                imageVector = it,
                tint = colors.primary,
                contentDescription = buttonState.buttonContentDescription

            )
        }
    }
}
