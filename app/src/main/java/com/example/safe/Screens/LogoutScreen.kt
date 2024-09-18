package com.andriodproject.safe.Screens

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController




@Composable
fun LogoutScreen(
    navHostController: NavHostController,
    logout: () -> Unit
) {

    logout.invoke()
}