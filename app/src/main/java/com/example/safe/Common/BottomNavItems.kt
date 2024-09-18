package com.andriodproject.safe.Common

import com.example.safe.R
import com.example.safe.ScreenRoutes.ScreenRoutes


sealed class BottomNavItems(
    val title : String,
    val route : String,
    val icon : Int
){
    data object HomeItem : BottomNavItems(
        title = "Home",
        route = ScreenRoutes.HomeScreen.route,
        icon = R.drawable.home
    )
    data object Post : BottomNavItems(
        title = "Post",
        route = ScreenRoutes.PostScreen.route,
        icon = R.drawable.add_post
    )
    data object Notification : BottomNavItems(
        title = "Notify",
        route = ScreenRoutes.NotificationScreen.route,
        icon = R.drawable.notification
    )
    data object Profile : BottomNavItems(
        title = "Profile",
        route = ScreenRoutes.ProfileScreen.route,
        icon = R.drawable.account
    )
    data object Contact : BottomNavItems(
        title = "Contact",
        route = ScreenRoutes.ProfileScreen.route,
        icon = R.drawable.contact
    )
    data object Logout : BottomNavItems(
        title = "Logout",
        route = ScreenRoutes.LogoutScreen.route,
        icon = R.drawable.logout
    )
}