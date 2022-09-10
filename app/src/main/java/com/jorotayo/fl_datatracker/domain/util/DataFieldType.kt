package com.jorotayo.fl_datatracker.domain.util

enum class DataFieldType(val type: String) {
    SHORT_TEXT("Short Text"),
    LONG_TEXT("Long Text"),
    BOOLEAN("2 Options"),
    DATE("Date"),
    TIME("Time"),
    COUNT("Count"),
    TRISTATE("Tri-state");
}