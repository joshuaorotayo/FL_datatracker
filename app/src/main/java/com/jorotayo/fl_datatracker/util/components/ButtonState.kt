package com.jorotayo.fl_datatracker.util.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.jorotayo.fl_datatracker.util.Dimen.one
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.zero

data class ButtonState(
    val type: ButtonType,
    val label: String,
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
    val disabledContentColor: Color = Green,
    val backgroundColor: Color = Transparent,
    val disabledBackgroundColor: Color = Transparent,
    val borderColor: Color = Transparent,
    val disabledBorderColor: Color = Transparent,
)

enum class ButtonType {
    PRIMARY,
    SECONDARY,
    TERTIARY,
    NEGATIVE,
}

private const val FULLY_ROUNDED = 100

@Composable
fun Button(buttonState: ButtonState) {
    val defaultPadding = xSmall
    val defaultContentPadding = PaddingValues(defaultPadding)

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
            contentDescription = buttonState.buttonContentDescription ?: buttonState.label
        },
        contentPadding = buttonState.contentPadding ?: defaultContentPadding
    ) {
        Text(
            text = buttonState.label
            //style = buttonState.textStyle,
        )
    }
}
