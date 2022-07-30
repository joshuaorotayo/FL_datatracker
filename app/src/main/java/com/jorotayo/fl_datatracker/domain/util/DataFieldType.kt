package com.jorotayo.fl_datatracker.domain.util

enum class DataFieldType(val type: String) {
    SHORTSTRING(type = "SHORTSTRING"),
    LONGSTRING(type = "LONGSTRING"),
    BOOLEAN(type = "BOOLEAN"),
    DATE(type = "DATE"),
    TIME(type = "TIME"),
    COUNT(type = "COUNT"),
    TRISTATE(type = "TRI-STATE"),
}