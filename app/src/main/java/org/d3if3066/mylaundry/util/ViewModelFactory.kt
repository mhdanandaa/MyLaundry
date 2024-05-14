package org.d3if3066.mylaundry.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3066.mylaundry.database.CustomerDao
import org.d3if3066.mylaundry.database.OrderDao
import org.d3if3066.mylaundry.database.ServiceDao
import org.d3if3066.mylaundry.database.UserDao
import org.d3if3066.mylaundry.ui.screen.customer.AddCustomerViewModel
import org.d3if3066.mylaundry.ui.screen.home.HomeViewModel
import org.d3if3066.mylaundry.ui.screen.login.LoginViewModel
import org.d3if3066.mylaundry.ui.screen.register.RegisterViewModel
import org.d3if3066.mylaundry.ui.screen.service.AddServiceViewModel
import org.d3if3066.mylaundry.ui.screen.transaction.AddTransactionViewModel

class ViewModelFactory(
    private val userDao: UserDao? = null,
    private val serviceDao: ServiceDao? = null,
    private val orderDao: OrderDao? = null,
    private val customerDao: CustomerDao? = null,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userDao!!) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userDao!!) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userDao!!) as T
        } else if (modelClass.isAssignableFrom(AddCustomerViewModel::class.java)) {
            return AddCustomerViewModel(customerDao!!) as T
        } else if (modelClass.isAssignableFrom(AddServiceViewModel::class.java)) {
            return AddServiceViewModel(serviceDao!!) as T
        } else if (modelClass.isAssignableFrom(AddTransactionViewModel::class.java)) {
            return AddTransactionViewModel(
                serviceDao = serviceDao!!,
                orderDao = orderDao!!,
                customerDao = customerDao!!
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}