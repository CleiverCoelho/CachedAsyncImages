package com.cleivercoelho.skeleton.presentation.navigation

import Route
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cleivercoelho.skeleton.presentation.ui.screens.HomeScreen
import com.cleivercoelho.skeleton.presentation.ui.screens.UserDetailScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Route = Route.Home
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<Route.Home> {
            HomeScreen(
                onNavigateToUserDetail = { userId ->
                    navController.navigate(Route.UserDetail(userId))
                },
                onNavigateToSettings = {
                    navController.navigate(Route.Settings)
                }
            )
        }

        composable<Route.UserDetail> {
            UserDetailScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}