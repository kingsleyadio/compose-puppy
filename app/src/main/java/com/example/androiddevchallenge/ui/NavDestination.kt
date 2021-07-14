package com.example.androiddevchallenge.ui

import androidx.navigation.NavController

sealed class NavDestination(val route: String) {

    object Starter : NavDestination("starter")

    object PupList : NavDestination("pup-list")

    object PupDetails : NavDestination("pup-details/{id}") {
        fun materialize(pupId: Int) = "pup-details/$pupId"
    }
}

class NavActions(private val navController: NavController) {

    fun goHome() = navController.navigate(NavDestination.PupList.route) {
        popUpTo(NavDestination.Starter.route) { inclusive = true }
    }

    fun openDetails(pupId: Int) = navController.navigate(NavDestination.PupDetails.materialize(pupId))
}
