package com.cleivercoelho.skeleton.presentation.navigation

sealed interface Route {
    data object Home : Route

    data class UserDetail(val userId: Int) : Route

    data object Settings : Route
}