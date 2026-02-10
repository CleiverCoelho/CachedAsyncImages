package com.cleivercoelho.skeleton.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cleivercoelho.skeleton.core.navigation.Route
import com.cleivercoelho.skeleton.feature.home.HomeScreen

fun NavController.navigateToHome() {
    navigate(Route.Home) {
        popUpTo(graph.startDestinationId) {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.homeScreen(
    onNavigateToUserDetail: (Int) -> Unit,
    onNavigateToSettings: () -> Unit
) {
    composable<Route.Home> {
        HomeScreen(
            onNavigateToUserDetail = onNavigateToUserDetail,
            onNavigateToSettings = onNavigateToSettings
        )
    }
}
