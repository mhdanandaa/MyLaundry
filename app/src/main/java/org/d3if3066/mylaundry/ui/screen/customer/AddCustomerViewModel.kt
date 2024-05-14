package org.d3if3066.mylaundry.ui.screen.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.d3if3066.mylaundry.database.CustomerDao
import org.d3if3066.mylaundry.model.Customer

class AddCustomerViewModel(private val customerDao: CustomerDao) : ViewModel() {
    suspend fun createCustomer(name:String,phone:String):Boolean{
        viewModelScope.launch {
            customerDao.insert(Customer(name = name, phone = phone))
        }
        return true
    }
}