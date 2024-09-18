package com.andriodproject.safe.Screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.andriodproject.safe.Common.BottomNavItems
import com.andriodproject.safe.Graphs.HomeNavGraph
import com.example.safe.R
import com.example.safe.common.PreferencesManager
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    logout: () -> Unit
) {
    val items = listOf(
        BottomNavItems.HomeItem,
        BottomNavItems.Profile,
        BottomNavItems.Post,

        BottomNavItems.Notification,
        BottomNavItems.Logout
    )
    val context = LocalContext.current
    val userid = remember { PreferencesManager.retrieveData(context, "userid") ?: "No User ID Found" }
    val username = remember { PreferencesManager.retrieveData(context, "username") ?: "No Username Found" }
    // Remember the selected item and drawer state
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Image
                    Image(
                        painter = painterResource(id = R.drawable.userprofile), // Replace R.drawable.logo with your image resource ID
                        contentDescription = "Logo",
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Static Name
                    Text(
                        text = username,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp)) // Space (margin) from the top
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.title) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            // Navigate to the selected item's route
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                            // Update the selected index and close the drawer
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding) // Padding between items
                    )
                }
            }
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = { // TopBar to show title and menu icon
                TopAppBar(
                    title = {
                        Text(text = "Safe Zone Dashboard")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon( // Show Menu Icon on TopBar
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }, actions = {

                        IconButton(onClick = {
                            // Handle the back button click event
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Back"
                            )
                        }








                    }
                )
            }
        ) {
            HomeNavGraph(
                navController = navController,
                logout = logout
            )
        }
    }
}

// Helper function to get the current destination route
@Composable
fun currentDestination(navController: androidx.navigation.NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
