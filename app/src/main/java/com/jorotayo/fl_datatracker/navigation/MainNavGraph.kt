package com.jorotayo.fl_datatracker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jorotayo.fl_datatracker.screens.dataEntryScreen.DataEntryScreen
import com.jorotayo.fl_datatracker.screens.dataFieldsScreen.DataFieldsScreen
import com.jorotayo.fl_datatracker.screens.homeScreen.HomeScreen

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainNavGraph(
    navController: NavHostController,
    sheetState: ModalBottomSheetState
) {

    NavHost(
        navController = navController,
        route = "main_nav",
        startDestination = "home_screen"
    ) {
        composable(route = MainScreens.HomeMainScreens.route) {
            HomeScreen(navController = navController)
        }
        settingsNavGraph(navController)
        composable(
            route = MainScreens.DataEntry.route + "?id={dataId}",
            arguments = listOf(
                navArgument(
                    name = "dataId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
            )
        ) {
            DataEntryScreen(
                navController = navController,
                sheetState = sheetState
            )
        }
        composable(
            route = MainScreens.DataFieldsMainScreens.route
        ) {
            DataFieldsScreen()
        }
        /*  composable(
              route = MainScreens.DataFieldsMainScreens.route
          ) {

              DataFieldsReworkView()
          }*/
    }
}
