package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

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