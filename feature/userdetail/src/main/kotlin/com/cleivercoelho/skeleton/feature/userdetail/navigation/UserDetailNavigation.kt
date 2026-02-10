package com.cleivercoelho.skeleton.feature.userdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cleivercoelho.skeleton.core.navigation.Route
import com.cleivercoelho.skeleton.feature.userdetail.UserDetailScreen

fun NavController.navigateToUserDetail(userId: Int) {
    navigate(Route.UserDetail(userId = userId))
}

fun NavGraphBuilder.userDetailScreen(
    onNavigateBack: () -> Unit
) {
    composable<Route.UserDetail> {
        UserDetailScreen(onNavigateBack = onNavigateBack)
    }
}
