package com.example.safe.Graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.safe.ScreenRoutes.ScreenRoutes
import com.example.safe.Screens.LoginScreen
import com.example.safe.Screens.RegisterScreen
import com.example.safe.Screens.SplashScreen


fun NavGraphBuilder.AuthNavGraph(navController: NavController) {
    navigation(
    startDestination = ScreenRoutes.SplashScreen.route,
    route = ScreenRoutes.AuthNav.route
){
    composable(route = ScreenRoutes.SplashScreen.route){
        SplashScreen(navController = navController)
    }

    composable(route = ScreenRoutes.LoginScreen.route){
        LoginScreen(navController = navController)
    }
    composable(route = ScreenRoutes.SignUpScreen.route){
        RegisterScreen(navController = navController)
    }
}
}