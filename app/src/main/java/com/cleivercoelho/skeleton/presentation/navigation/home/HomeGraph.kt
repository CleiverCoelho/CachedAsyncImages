package com.cleivercoelho.skeleton.presentation.navigation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cleivercoelho.skeleton.presentation.ui.screens.HomeScreen
import com.cleivercoelho.skeleton.presentation.ui.screens.UserDetailScreen

fun NavGraphBuilder.homeGraph(navController: NavController) {
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