package com.jorotayo.fl_datatracker.domain.util

enum class DataFieldType(val type: String) {
    SHORTSTRING("Short String"),
    LONGSTRING("Long String"),
    BOOLEAN("Boolean"),
    DATE("Date"),
    TIME("Time"),
    COUNT("Count"),
    TRISTATE("Tri-state");
}