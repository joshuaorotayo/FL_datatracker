package com.jorotayo.fl_datatracker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DisplaySettings
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.ui.graphics.vector.ImageVector

sealed class SettingScreens(
    val route: String,
    val settingName: String,
    val settingDescription: String = "",
    val settingIcon: ImageVector = Icons.Filled.Close,
) {
    object DataFieldSettings : SettingScreens(
        route = "data_field_settings",
        settingName = "DataFields Settings",
        settingDescription = "Edit forms based on the Data Entry Screen",
        settingIcon = Icons.Default.Edit
    )

    object DisplaySettings : SettingScreens(
        route = "display_settings",
        settingName = "Display Settings",
        settingDescription = "Home Screen with page of data results",
        settingIcon = Icons.Filled.DisplaySettings
    )

    object FAQsList : SettingScreens(
        route = "faq_settings",
        settingName = "FAQs List",
        settingDescription = "Add/Edit or remove Data Fields Screen",
        settingIcon = Icons.Filled.QuestionAnswer
    )
}