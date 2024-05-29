package org.d3if3066.mylaundry.navigation

import org.d3if3066.mylaundry.ui.screen.home.KEY_NEXT_PAGE
import org.d3if3066.mylaundry.ui.screen.transaction.KEY_ID_ORDER

sealed class Screen(val route:String) {
    data object Login: Screen("loginScreen")
    data object Register: Screen("registerScreen")
    data object Home: Screen("homeScreen/{$KEY_NEXT_PAGE}"){
        fun withRedirect(id: String) = "homeScreen/$id"
    }

    data object AddTransaction: Screen("addTransactionScreen")
    data object AddCustomer: Screen("addCustomerScreen")
    data object AddService: Screen("addServiceScreen")

    data object TransactionList: Screen("transactionList")
    data object ServiceList: Screen("serviceList")
    data object CustomerList: Screen("customerList")

    data object DetailTransaction: Screen ("detailTransaction/{$KEY_ID_ORDER}") {
        fun withId(id: Long) = "detailTransaction/$id"
    }


}