package com.cleivercoelho.skeleton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.cleivercoelho.skeleton.core.designsystem.theme.SkeletonTheme
import com.cleivercoelho.skeleton.core.navigation.Route
import com.cleivercoelho.skeleton.feature.home.navigation.homeScreen
import com.cleivercoelho.skeleton.feature.userdetail.navigation.navigateToUserDetail
import com.cleivercoelho.skeleton.feature.userdetail.navigation.userDetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkeletonTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.Home
                ) {
                    homeScreen(
                        onNavigateToUserDetail = { userId ->
                            navController.navigateToUserDetail(userId)
                        },
                        onNavigateToSettings = { /* TODO */ }
                    )
                    userDetailScreen(
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}