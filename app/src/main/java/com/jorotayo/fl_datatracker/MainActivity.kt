package com.jorotayo.fl_datatracker

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jorotayo.fl_datatracker.domain.model.DataField
import com.jorotayo.fl_datatracker.domain.model.Preset
import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.navigation.SetupNavGraph
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.viewModels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.objectbox.Box
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    private lateinit var dataFieldBox: Box<DataField>
    private lateinit var settingBox: Box<Setting>
    private lateinit var presetBox: Box<Preset>

    override fun onCreate(savedInstanceState: Bundle?) {

        var keepSplashOnScreen = true
        val delay = 1000L

        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)

        super.onCreate(savedInstanceState)

        dataFieldBox = ObjectBox.get().boxFor(DataField::class.java)
        settingBox = ObjectBox.get().boxFor(Setting::class.java)
        presetBox = ObjectBox.get().boxFor(Preset::class.java)

        if (dataFieldBox.count() < 20) {
            //dataFieldsBox.removeAll()
            //dataFieldsBox.put(initFakeData())
        }

        setContent {
            FL_DatatrackerTheme {
                val screen by splashViewModel.startDestination
                val navController = rememberNavController()
                SetupNavGraph(navController = navController, startDestination = screen)
            }
        }
    }
}