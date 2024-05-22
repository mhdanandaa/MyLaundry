package org.d3if3066.mylaundry.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3066.mylaundry.ui.screen.customer.AddCustomerScreen
import org.d3if3066.mylaundry.ui.screen.customer.CustomerListScreen
import org.d3if3066.mylaundry.ui.screen.home.HomeScreen
import org.d3if3066.mylaundry.ui.screen.login.LoginScreen
import org.d3if3066.mylaundry.ui.screen.register.RegisterScreen
import org.d3if3066.mylaundry.ui.screen.service.AddServiceScreen
import org.d3if3066.mylaundry.ui.screen.service.ServiceListScreen
import org.d3if3066.mylaundry.ui.screen.transaction.AddTransactionScreen
import org.d3if3066.mylaundry.ui.screen.transaction.TransactionListScreen

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
        composable(route = Screen.TransactionList.route) {
            TransactionListScreen(navHostController)
        }
        composable(route = Screen.ServiceList.route) {
            ServiceListScreen(navHostController)
        }
        composable(route = Screen.CustomerList.route) {
            CustomerListScreen(navHostController)
        }
    }
}