package org.d3if3066.mylaundry.navigation

sealed class Screen(val route:String) {
    data object Login: Screen("loginScreen")
    data object Register: Screen("registerScreen")
    data object Home: Screen("homeScreen")
}