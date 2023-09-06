package com.jorotayo.fl_datatracker.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.states.NewDataFieldState
import com.jorotayo.fl_datatracker.ui.theme.md_theme_light_primary
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


fun capitaliseWord(word: String): String {
    return word.lowercase(Locale.ROOT)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun getHeaderColour(isDark: Boolean): Color {
    return if (isDark) Color.Gray else md_theme_light_primary
}

fun TextFieldValue.ofMaxLength(maxLength: Int): TextFieldValue {
    val overLength = text.length - maxLength
    return if (overLength > 0) {
        val headIndex = selection.end - overLength
        val trailIndex = selection.end
        // Under normal conditions, headIndex >= 0
        if (headIndex >= 0) {
            copy(
                text = text.substring(0, headIndex) + text.substring(trailIndex, text.length),
                selection = TextRange(headIndex)
            )
        } else {
            // exceptional
            copy(text.take(maxLength), selection = TextRange(maxLength))
        }
    } else {
        this
    }
}

fun returnNewDataField(newDataFieldState: NewDataFieldState): DataField {
    return DataField(
        dataFieldId = newDataFieldState.dataFieldId,
        presetId = newDataFieldState.presetId,
        fieldName = newDataFieldState.fieldName,
        dataFieldType = newDataFieldState.fieldType,
        first = newDataFieldState.firstValue,
        second = newDataFieldState.secondValue,
        third = newDataFieldState.thirdValue,
        fieldHint = newDataFieldState.fieldHint,
        isEnabled = newDataFieldState.isEnabled
    )
}
