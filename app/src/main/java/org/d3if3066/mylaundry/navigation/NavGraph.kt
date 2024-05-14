package org.d3if3066.mylaundry.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if3066.mylaundry.ui.screen.customer.AddCustomerScreen
import org.d3if3066.mylaundry.ui.screen.home.HomeScreen
import org.d3if3066.mylaundry.ui.screen.login.LoginScreen
import org.d3if3066.mylaundry.ui.screen.register.RegisterScreen
import org.d3if3066.mylaundry.ui.screen.service.AddServiceScreen
import org.d3if3066.mylaundry.ui.screen.transaction.AddTransactionScreen

@RequiresApi(Build.VERSION_CODES.N)
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
            AddTransactionScreen(navHostController)
        }
        composable(route = Screen.Customer.route) {
            AddCustomerScreen(navHostController)
        }
        composable(route = Screen.Service.route) {
            AddServiceScreen(navHostController)
        }
    }
}