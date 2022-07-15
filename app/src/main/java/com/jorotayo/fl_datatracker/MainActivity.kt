package com.jorotayo.fl_datatracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jorotayo.fl_datatracker.domain.model.SettingsBool
import com.jorotayo.fl_datatracker.domain.model.SettingsBool_
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.navigation.SetupNavGraph
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import io.objectbox.Box

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @kotlin.OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        val settingBox: Box<SettingsBool> = ObjectBox.get().boxFor(SettingsBool::class.java)
        val query =
            settingBox.query(SettingsBool_.settingName.equal("isOnBoardingComplete")).build()
        val isOnBoardingComplete = query.findFirst()

        setContent {
            FL_DatatrackerTheme {
                val navController = rememberNavController()

                val screen = if (isOnBoardingComplete!!.settingValue) {
                    Screen.Home.route
                } else {
                    Screen.OnBoarding.route
                }
                SetupNavGraph(navController = navController, startDestination = screen)
            }
        }
    }
}