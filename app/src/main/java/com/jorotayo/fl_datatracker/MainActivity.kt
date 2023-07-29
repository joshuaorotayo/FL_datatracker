package com.jorotayo.fl_datatracker

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jorotayo.fl_datatracker.domain.util.SettingsKeys
import com.jorotayo.fl_datatracker.domain.util.UserPreferenceStore
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
    lateinit var userPreferenceStore: UserPreferenceStore

    val selectPictureLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
        }

    val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        var keepSplashOnScreen = true
        val delay = 1000L

        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)

        super.onCreate(savedInstanceState)

        initialiseApp()

        setContent {
            FL_DatatrackerTheme {
                val screen by splashViewModel.startDestination
                SetupNavGraph(startDestination = screen)
            }
        }
    }

    private fun initialiseApp() {
        if (!userPreferenceStore.getBoolean(SettingsKeys.ONBOARDING_COMPLETE)) {
            userPreferenceStore.setBoolean(Pair(SettingsKeys.ONBOARDING_COMPLETE, false))
            userPreferenceStore.setString(Pair(SettingsKeys.CURRENT_PRESET, "Default"))
        }
    }
//        val currentPreset = keyValueStore.getString(SettingsKeys.CURRENT_PRESET)

}