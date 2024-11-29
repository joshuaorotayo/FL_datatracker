package com.jorotayo.fl_datatracker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jorotayo.fl_datatracker.MainScreen
import com.jorotayo.fl_datatracker.screens.onboarding.OnboardingScreen
import com.jorotayo.fl_datatracker.screens.onboarding.components.OnboardingEvent
import com.jorotayo.fl_datatracker.screens.onboarding.components.OnboardingScreenData

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun OnboardingNavGraph(
    onboardingEvent: (OnboardingEvent) -> Unit,
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("onboarding_screen") {
            OnboardingScreen(
                onBoardingEvent = { onboardingEvent(OnboardingEvent.SaveOnBoarding) },
                finishOnboarding = { navController.navigate("main_nav") },
                pages = getWelcomePages()
            )
        }

        composable("main_nav") {
            MainScreen()
        }
    }
}

private fun getWelcomePages(): List<OnboardingScreenData> {
    val pages = ArrayList<OnboardingScreenData>()
    pages.add(
        OnboardingScreenData(
            image = Icons.Default.SwapHoriz,
            title = "Swipe To Navigate",
            description = "Swipe on the images to traverse through the welcoming screen"
        )
    )
    pages.add(
        OnboardingScreenData(
            image = Icons.Default.Add,
            title = "Add Data",
            description = "Click the floating action button to add new Data"
        )
    )
    pages.add(
        OnboardingScreenData(
            image = Icons.Default.Checklist,
            title = "Enable/Disable Fields",
            description = "Configure the fields you wish to Enable and Disable in Settings"
        )
    )
    pages.add(
        OnboardingScreenData(
            image = Icons.Default.QueryStats,
            title = "View Data",
            description = "Have an overview of Data entered from the home-screen"
        )
    )
    pages.add(
        OnboardingScreenData(
            image = Icons.Default.AutoGraph,
            title = "Statistics",
            description = "View the breakdown of information in statistical form."
        )
    )
    return pages
}
