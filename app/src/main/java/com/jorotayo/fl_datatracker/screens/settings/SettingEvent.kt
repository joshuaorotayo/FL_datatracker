package com.jorotayo.fl_datatracker.screens.settings

sealed class SettingEvent {
    object DataFieldSettings : SettingEvent()
    object DisplaySettings : SettingEvent()
    object FAQsList : SettingEvent()
}
