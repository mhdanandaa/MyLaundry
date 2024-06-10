package org.d3if3066.mylaundry.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3066.mylaundry.database.MyLaundryDb
import org.d3if3066.mylaundry.ui.screen.customer.AddCustomerScreen
import org.d3if3066.mylaundry.ui.screen.customer.CustomerListScreen
import org.d3if3066.mylaundry.ui.screen.home.HomeScreen
import org.d3if3066.mylaundry.ui.screen.login.LoginScreen
import org.d3if3066.mylaundry.ui.screen.login.LoginViewModel
import org.d3if3066.mylaundry.ui.screen.register.RegisterScreen
import org.d3if3066.mylaundry.ui.screen.service.AddServiceScreen
import org.d3if3066.mylaundry.ui.screen.service.ServiceListScreen
import org.d3if3066.mylaundry.ui.screen.transaction.AddTransactionScreen
import org.d3if3066.mylaundry.ui.screen.transaction.DetailTransaction
import org.d3if3066.mylaundry.ui.screen.transaction.KEY_ID_ORDER
import org.d3if3066.mylaundry.ui.screen.transaction.TransactionListScreen
import org.d3if3066.mylaundry.util.ViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navHostController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val db = MyLaundryDb.getInstance(context)
    val factory = ViewModelFactory(db.userDao)
    val viewModel: LoginViewModel = viewModel(factory = factory)
    var startDestination by remember {
        mutableStateOf(Screen.Home.route)
    }
    if (!viewModel.checkIsSignedIn()) {
        startDestination = Screen.Login.route
    }
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navHostController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navHostController)
        }
        composable(route = Screen.Home.route,) {
            HomeScreen(navHostController)
        }
        composable(route = Screen.AddTransaction.route) {
            AddTransactionScreen(navHostController)
        }
        composable(route = Screen.AddCustomer.route) {
            AddCustomerScreen(navHostController)
        }
        composable(route = Screen.AddService.route) {
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

        composable(
            route = Screen.DetailTransaction.route,
            arguments = listOf(
                navArgument(KEY_ID_ORDER) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_ORDER)
            if (id != null) {
                DetailTransaction(navHostController, id)
            } else {
                navHostController.navigate(Screen.TransactionList.route)
            }
        }
    }
}