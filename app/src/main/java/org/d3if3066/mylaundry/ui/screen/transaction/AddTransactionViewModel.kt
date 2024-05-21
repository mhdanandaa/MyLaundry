package org.d3if3066.mylaundry.ui.screen.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.d3if3066.mylaundry.database.CustomerDao
import org.d3if3066.mylaundry.database.OrderDao
import org.d3if3066.mylaundry.database.ServiceDao
import org.d3if3066.mylaundry.model.Customer
import org.d3if3066.mylaundry.model.Order
import org.d3if3066.mylaundry.model.Service

class AddTransactionViewModel(
    private val orderDao: OrderDao,
    private val customerDao: CustomerDao,
    private val serviceDao: ServiceDao
) : ViewModel() {
    val orderList: StateFlow<List<Order>> = orderDao.getOrder().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val serviceList:StateFlow<List<Service>> = serviceDao.getAllService().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val customerList:StateFlow<List<Customer>> = customerDao.getAllCustomer().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    suspend fun createTransaction(
        customerName: String,
        weight: Int,
        serviceName: String,
        startDate: String,
        endDate: String,
        price: Int
    ): Boolean {
        var customer = customerDao.getCustomerByName(customerName)
        var service = serviceDao.getServicerByName(serviceName)
        if (customer == null) {
            customerDao.insert(Customer(name = customerName))
            customer = customerDao.getCustomerByName(customerName)
        }
        if (service == null) {
            return false
        } else orderDao.insert(
            Order(
                customerId = customer!!.id,
                weight = weight,
                serviceId = service.id,
                startDate = startDate,
                endDate = endDate,
                price = price
            )
        )
        return true
    }
    suspend fun getOrder(id: Long): Order? {
        return orderDao.getOrderById(id)
    }
    fun getCustomerById(customerId: Long): StateFlow<Customer?> {
        val customerFlow = MutableStateFlow<Customer?>(null)
        viewModelScope.launch {
            customerFlow.value = customerDao.getCustomerById(customerId)
        }
        return customerFlow
    }
    fun getServiceById(serviceId: Long): StateFlow<Service?> {
        val serviceFlow = MutableStateFlow<Service?>(null)
        viewModelScope.launch {
            serviceFlow.value = serviceDao.getServiceById(serviceId)
        }
        return serviceFlow
    }

}

