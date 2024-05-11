package org.d3if3066.mylaundry.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if3066.mylaundry.ui.screen.AddCustsScreen
import org.d3if3066.mylaundry.ui.screen.AddServicesScreen
import org.d3if3066.mylaundry.ui.screen.AddTransScreen
import org.d3if3066.mylaundry.ui.screen.HomeScreen
import org.d3if3066.mylaundry.ui.screen.LoginScreen
import org.d3if3066.mylaundry.ui.screen.RegisterScreen

@Composable
fun SetupNavGraph(navHostController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navHostController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navHostController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navHostController)
        }
        composable(route =  Screen.Transaction.route) {
            AddTransScreen(navHostController)
        }
        composable(route = Screen.Customer.route) {
            AddCustsScreen(navHostController)
        }
        composable(route = Screen.Service.route) {
            AddServicesScreen(navHostController)
        }
    }
}