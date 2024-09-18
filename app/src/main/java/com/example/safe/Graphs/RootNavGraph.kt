package com.example.safe.Graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andriodproject.safe.Screens.HomeScreen

import com.example.safe.ScreenRoutes.ScreenRoutes


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.AuthNav.route
    ) {
        AuthNavGraph(navController)

        composable(route = ScreenRoutes.HomeNav.route){
            HomeScreen (
                logout = {
                    navController.navigate(ScreenRoutes.AuthNav.route) {
                        popUpTo(0){}
                    }
                }
            )
        }
    }

}