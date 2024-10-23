package com.jorotayo.fl_datatracker.screens.settings

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.navigation.SettingScreens
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SettingRow(
    setting: SettingScreens,
    onSettingSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimens.xxSmall, vertical = dimens.xSmall)
            .clickable(onClick = onSettingSelected),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(dimens.medium)
                .weight(dimens.twentyPercent)
                .padding(end = dimens.xxxSmall),
            imageVector = setting.settingIcon,
            contentDescription = "icon for ${setting.settingDescription}",
            tint = MaterialTheme.colors.primary
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(dimens.eightyPercent)
        ) {
            Text(
                text = setting.settingName,
                style = AppTheme.typography.titleSmall,
                textAlign = TextAlign.Start,
                color = AppTheme.colors.onSurface
            )
            Text(
                text = setting.settingDescription,
                style = AppTheme.typography.body,
                textAlign = TextAlign.Start,
                color = AppTheme.colors.onSurface
            )
        }
    }
}

@Composable
fun SettingDivider() {
    Divider(
        modifier = Modifier
            .padding(start = dimens.regular)
            .fillMaxWidth(),
        color = MaterialTheme.colors.secondary,
        thickness = 0.5.dp
    )
}
