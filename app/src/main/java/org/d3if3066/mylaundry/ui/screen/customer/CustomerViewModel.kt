package org.d3if3066.mylaundry.ui.screen.customer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.d3if3066.mylaundry.database.CustomerDao
import org.d3if3066.mylaundry.model.Customer

class CustomerViewModel(private val customerDao: CustomerDao) : ViewModel() {
    val customerList: StateFlow<List<Customer>> = customerDao.getAllCustomer().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    suspend fun createCustomer(name:String,phone:String):Boolean{
        viewModelScope.launch {
            customerDao.insert(Customer(name = name, phone = phone))
        }
        return true
    }
    suspend fun deleteCustomer(customerId: Long) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                customerDao.deleteCustomerById(customerId)
            }
        } catch (e: Exception) {
            Log.d("CustomerViewModel", e.message.toString())
        }
    }
}