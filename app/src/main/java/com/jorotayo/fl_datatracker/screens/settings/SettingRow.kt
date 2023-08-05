package com.jorotayo.fl_datatracker.screens.settings

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.jorotayo.fl_datatracker.navigation.SettingScreens
import com.jorotayo.fl_datatracker.util.Dimen.medium
import com.jorotayo.fl_datatracker.util.Dimen.regular
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxxSmall

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SettingRow(
    setting: SettingScreens,
    onSettingSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(xxSmall)
            .clickable(onClick = onSettingSelected),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(medium)
                .weight(0.2f)
                .padding(end = xxxSmall),
            imageVector = setting.settingIcon,
            contentDescription = "icon for ${setting.settingDescription}",
            tint = MaterialTheme.colors.primary
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
        ) {
            Text(
                text = setting.settingName,
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = setting.settingDescription,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
fun SettingDivider() {
    Divider(
        modifier = Modifier
            .padding(horizontal = regular)
            .fillMaxWidth()
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.2F))
    )
}
