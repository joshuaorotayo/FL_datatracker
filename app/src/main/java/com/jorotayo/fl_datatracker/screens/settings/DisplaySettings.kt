package com.jorotayo.fl_datatracker.screens.settings

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign.Companion.Start
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.screens.settings.states.DisplayUiState
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens
import com.jorotayo.fl_datatracker.util.DefaultPreviews

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@DefaultPreviews
@Composable
private fun PreviewDisplaySettings() {
    AppTheme {
        DisplaySettings(
            uiState = DisplayUiState(
                isSystemDarkLightEnabled = false,
                isLightShowing = false
            )
        )
    }
}

@Composable
fun DisplaySettings(
    uiState: DisplayUiState
) {
    val isLightMode = isSystemInDarkTheme().not()
    var showingLightMode by remember { mutableStateOf(isLightMode) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    )
    {
        Column {
            Text(
                modifier = Modifier
                    .padding(start = dimens.small, top = dimens.medium),
                text = "Display Settings",
                style = typography.h2,
                color = colors.onSurface,
                textAlign = Start
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(dimens.xSmall)
                    .background(colors.background),
                shape = RoundedCornerShape(dimens.xSmall),
                elevation = dimens.xxxSmall
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(dimens.small)
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Light & Dark Mode",
                        color = colors.secondary,
                        style = typography.h2,
                        textAlign = Start
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimens.small),
                        Arrangement.spacedBy(dimens.medium)
                    ) {
                        Column(
                            Modifier.weight(1f)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth(),
                                imageVector = Icons.Default.Face,
                                contentDescription = "Light Mode Image images"
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                Arrangement.SpaceAround,
                                Alignment.CenterVertically
                            ) {
                                Text(text = "Light Mode")
                                Switch(
                                    checked = showingLightMode,
                                    enabled = uiState.isSystemDarkLightEnabled.not(),
                                    onCheckedChange = { showingLightMode = !showingLightMode },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = colors.primary,
                                        checkedTrackColor = colors.primary,
                                        uncheckedThumbColor = colors.primary,
                                        uncheckedTrackColor = Color.DarkGray,
                                        checkedTrackAlpha = dimens.twentyPercent,
                                        uncheckedTrackAlpha = dimens.twentyPercent
                                    )
                                )
                            }
                        }
                        Column(
                            Modifier.weight(1f)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth(),
                                imageVector = Icons.Default.Face,
                                contentDescription = "Dark Mode image"
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                Arrangement.SpaceAround,
                                Alignment.CenterVertically
                            ) {
                                Text(text = "Dark Mode")
                                Switch(
                                    checked = showingLightMode.not(),
                                    enabled = uiState.isSystemDarkLightEnabled.not(),
                                    onCheckedChange = { showingLightMode = !showingLightMode },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = colors.primary,
                                        checkedTrackColor = colors.primary,
                                        uncheckedThumbColor = colors.primary,
                                        uncheckedTrackColor = Color.DarkGray,
                                        checkedTrackAlpha = dimens.twentyPercent,
                                        uncheckedTrackAlpha = dimens.twentyPercent
                                    )
                                )
                            }
                        }
                    }
                    Divider(modifier = Modifier.fillMaxWidth())
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(top = dimens.small),
                            text = "Follow System Colours",
                            style = AppTheme.typography.titleSmall
                        )
                        Switch(
                            checked = uiState.isSystemDarkLightEnabled,
                            onCheckedChange = { showingLightMode = !showingLightMode },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colors.primary,
                                checkedTrackColor = colors.primary,
                                uncheckedThumbColor = colors.primary,
                                uncheckedTrackColor = Color.DarkGray,
                                checkedTrackAlpha = dimens.twentyPercent,
                                uncheckedTrackAlpha = dimens.twentyPercent
                            )
                        )
                    }
                    Text(
                        text = "This will follow the system colours to set the light and dark mode for the app",
                        color = colors.secondary
                    )

                }
            }
        }
    }
}
