package com.cleivercoelho.skeleton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cleivercoelho.skeleton.presentation.navigation.AppNavHost
import com.cleivercoelho.skeleton.presentation.ui.theme.SkeletonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkeletonTheme {
                AppNavHost()
            }
        }
    }
}