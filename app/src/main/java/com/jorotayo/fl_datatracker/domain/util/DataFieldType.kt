package com.jorotayo.fl_datatracker.domain.util

enum class DataFieldType(val type: String) {
    BOOLEAN(type = "BOOLEAN"),
    DATE(type = "DATE"),
    TIME(type = "TIME"),
    SHORTSTRING(type = "SHORTSTRING"),
    LONGSTRING(type = "LONGSTRING"),
    COUNT(type = "COUNT"),
    TRISTATE(type = "TRI-STATE"),
}