package org.d3if3066.mylaundry.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3066.mylaundry.database.CustomerDao
import org.d3if3066.mylaundry.database.OrderDao
import org.d3if3066.mylaundry.database.ServiceDao
import org.d3if3066.mylaundry.database.UserDao
import org.d3if3066.mylaundry.ui.screen.customer.CustomerViewModel
import org.d3if3066.mylaundry.ui.screen.home.HomeViewModel
import org.d3if3066.mylaundry.ui.screen.login.LoginViewModel
import org.d3if3066.mylaundry.ui.screen.register.RegisterViewModel
import org.d3if3066.mylaundry.ui.screen.service.ServiceViewModel
import org.d3if3066.mylaundry.ui.screen.transaction.TransactionViewModel

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
            return HomeViewModel(userDao!!,orderDao!!) as T
        } else if (modelClass.isAssignableFrom(CustomerViewModel::class.java)) {
            return CustomerViewModel(customerDao!!) as T
        } else if (modelClass.isAssignableFrom(ServiceViewModel::class.java)) {
            return ServiceViewModel(serviceDao!!) as T
        } else if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(
                serviceDao = serviceDao!!,
                orderDao = orderDao!!,
                customerDao = customerDao!!
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}