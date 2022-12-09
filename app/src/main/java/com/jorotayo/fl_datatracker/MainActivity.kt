package com.jorotayo.fl_datatracker

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.useCases.PresetUseCases
import com.jorotayo.fl_datatracker.domain.useCases.SettingsUseCases
import com.jorotayo.fl_datatracker.navigation.SetupNavGraph
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.viewModels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    @Inject
    lateinit var settingsUseCases: SettingsUseCases

    @Inject
    lateinit var presetUseCases: PresetUseCases

    override fun onCreate(savedInstanceState: Bundle?) {

        var keepSplashOnScreen = true
        val delay = 1000L

        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)

        super.onCreate(savedInstanceState)


        val onBoardingCompleteSetting = Setting(
            settingId = 0,
            settingName = "isOnBoardingComplete",
            settingBoolValue = false,
            settingStringValue = ""
        )

        val settingsList = settingsUseCases.getSettingsList()
        if (!settingsList.contains(onBoardingCompleteSetting))
            settingsUseCases.addSetting(
                onBoardingCompleteSetting
            )

        val presetList = presetUseCases.getPresetList()
        if (presetList.isEmpty()) {
            presetUseCases.addPreset(
                Preset(
                    presetId = 1,
                    presetName = "Default"
                )
            )

            settingsUseCases.addSetting(
                Setting(
                    settingId = 1,
                    settingName = "currentPreset",
                    settingBoolValue = false,
                    settingStringValue = "Default"
                )
            )
        }

        setContent {
            FL_DatatrackerTheme {
                val screen by splashViewModel.startDestination
                SetupNavGraph(startDestination = screen)
            }
        }
    }
}