package com.andriodproject.safe.Graphs


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.andriodproject.safe.Screens.Dashboard
import com.andriodproject.safe.Screens.DetailScreen
import com.andriodproject.safe.Screens.LogoutScreen
import com.andriodproject.safe.Screens.NotificationScreen
import com.andriodproject.safe.Screens.PostScreen
import com.andriodproject.safe.Screens.ViewMyPostDetails

import com.example.safe.ScreenRoutes.ScreenRoutes
import com.safe.safe.Screens.ProfileScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeNavGraph(
    navController: NavHostController,
    logout: () -> Unit
) {


    NavHost(
        navController = navController,
        route = ScreenRoutes.HomeNav.route,
        startDestination = ScreenRoutes.HomeScreen.route
    ) {
        composable(route = ScreenRoutes.PostScreen.route){
            PostScreen(navHostController = navController)
        }
        composable(route = ScreenRoutes.HomeScreen.route){
            Dashboard(navHostController = navController)
        }
        composable(
            route = ScreenRoutes.DetailScreen.route,
            arguments = listOf(
                navArgument("latitude") { type = NavType.StringType },
                navArgument("longitude") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val latitude = backStackEntry.arguments?.getString("latitude") ?: ""
            val longitude = backStackEntry.arguments?.getString("longitude") ?:""
            DetailScreen(navHostController = navController, latitude = latitude, longitude = longitude)
        }

        composable(
            route = ScreenRoutes.ViewMyPost.route,
            arguments = listOf(
                navArgument("userid") { type = NavType.StringType },

            )
        ) { backStackEntry ->
            val userid = backStackEntry.arguments?.getString("userid") ?: ""

            ViewMyPostDetails(navHostController = navController, userid = userid)
        }
        composable(route = ScreenRoutes.ProfileScreen.route){
            ProfileScreen(navHostController = navController)
            //this is profile screen
        }
        composable(route = ScreenRoutes.NotificationScreen.route){
            NotificationScreen(navHostController = navController)
        }


        composable(route = ScreenRoutes.LogoutScreen.route){
            LogoutScreen(navHostController = navController, logout = logout)
        }
    }
}