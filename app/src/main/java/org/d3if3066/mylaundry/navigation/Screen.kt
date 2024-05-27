package org.d3if3066.mylaundry.navigation

import org.d3if3066.mylaundry.ui.screen.transaction.KEY_ID_ORDER

sealed class Screen(val route:String) {
    data object Login: Screen("loginScreen")
    data object Register: Screen("registerScreen")
    data object Home: Screen("homeScreen")

    data object Transaction: Screen("addTransScreen")
    data object Customer: Screen("addCustsScreen")
    data object Service: Screen("addServicesScreen")

    data object TransactionList: Screen("transactionList")
    data object ServiceList: Screen("serviceList")
    data object CustomerList: Screen("customerList")

    data object DetailTransaction: Screen ("detailTransaction/{$KEY_ID_ORDER}") {
        fun withId(id: Long) = "detailTransaction/$id"
    }


}