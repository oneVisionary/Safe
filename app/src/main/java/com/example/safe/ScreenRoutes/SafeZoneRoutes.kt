package com.example.safe.ScreenRoutes

sealed class ScreenRoutes(val route : String) {
    //Screen Routes
    data object SplashScreen : ScreenRoutes("splash_screen")

    data object LoginScreen : ScreenRoutes("login_screen")
    data object SignUpScreen : ScreenRoutes("signup_screen")
    data object HomeScreen : ScreenRoutes("home_screen")

    data object PostScreen : ScreenRoutes("post_screen")
    data object ProfileScreen : ScreenRoutes("profile_screen")
    data object NotificationScreen : ScreenRoutes("notification_screen")

    object DetailScreen : ScreenRoutes("detail_screen/{latitude}/{longitude}") {
        fun createRoute(latitude: Double, longitude: Double) =
            "detail_screen/$latitude/$longitude"
    }
    object ViewMyPost : ScreenRoutes("viewMyPost/{userid}") {
        fun createRoute(userid: String) =
            "viewMyPost/$userid"
    }
    data object ContactScreen : ScreenRoutes("contact_screen")

    data object LogoutScreen : ScreenRoutes("logout_screen")

    //Graph Routes
    data object AuthNav : ScreenRoutes("AUTH_NAV_GRAPH")

    data object HomeNav : ScreenRoutes("HOME_NAV_GRAPH")
}