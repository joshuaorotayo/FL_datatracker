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
import com.jorotayo.fl_datatracker.domain.util.DataFieldType
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

    private val dataFieldsBox: Box<DataField> = ObjectBox.get().boxFor(DataField::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {

        var keepSplashOnScreen = true
        val delay = 1000L

        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)

        super.onCreate(savedInstanceState)

        if (dataFieldsBox.count() < 20) {
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

fun initFakeData(): List<DataField> {
    return listOf(

        DataField(
            id = 0,
            fieldName = "Service Name",
            dataFieldType = DataFieldType.SHORTSTRING.ordinal,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 0,
            fieldName = "Preacher",
            dataFieldType = DataFieldType.SHORTSTRING.ordinal,
            dataValue = "",
            isEnabled = false
        ),
        DataField(
            id = 0,
            fieldName = "Date",
            dataFieldType = DataFieldType.DATE.ordinal,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 0,
            fieldName = "Time",
            dataFieldType = DataFieldType.TIME.ordinal,
            dataValue = "",
            isEnabled = false
        ),
        DataField(
            id = 0,
            fieldName = "Attendance",
            dataFieldType = DataFieldType.COUNT.ordinal,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 0,
            fieldName = "Tithe Payers",
            dataFieldType = DataFieldType.COUNT.ordinal,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 0,
            fieldName = "Communion",
            dataFieldType = DataFieldType.BOOLEAN.ordinal,
            dataValue = "",
            isEnabled = true
        ),
        DataField(
            id = 0,
            fieldName = "J-SCHOOL",
            dataFieldType = DataFieldType.TRISTATE.ordinal,
            dataValue = "",
            isEnabled = false
        ),
        DataField(
            id = 0,
            fieldName = "Preaching Notes",
            dataFieldType = DataFieldType.LONGSTRING.ordinal,
            dataValue = "",
            isEnabled = true
        )
    )
}