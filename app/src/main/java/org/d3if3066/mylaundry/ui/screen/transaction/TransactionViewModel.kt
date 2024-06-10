package org.d3if3066.mylaundry.ui.screen.transaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
import org.d3if3066.mylaundry.model.OrderDetail
import org.d3if3066.mylaundry.model.Service

class TransactionViewModel(
    private val orderDao: OrderDao,
    private val customerDao: CustomerDao,
    private val serviceDao: ServiceDao
) : ViewModel() {
    val orderList: StateFlow<List<Order>> = orderDao.getOrder().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val orderDetailList: StateFlow<List<OrderDetail>> = orderDao.getAllOrderDetail().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val serviceList: StateFlow<List<Service>> = serviceDao.getAllService().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val customerList: StateFlow<List<Customer>> = customerDao.getAllCustomer().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    suspend fun createTransaction(
        customerName: String,
        customerPhone: String,
        weight: Int,
        serviceName: String,
        startDate: String,
        endDate: String,
        price: Int
    ): Boolean {
        var customer = customerDao.getCustomerByName(customerName)
        var service = serviceDao.getServicerByName(serviceName)
        if (customer == null) {
            customerDao.insert(Customer(name = customerName, phone = customerPhone))
            customer = customerDao.getCustomerByName(customerName)
        } else {
            if (customerPhone !== "") {
                customer.phone = customerPhone
                customerDao.update(customer)
            }
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

    fun getOrderDetailById(orderId: Long): OrderDetail? {
        var orderDetailFlow: OrderDetail? = null
        orderDetailFlow = orderDao.getOrderDetailById(orderId)
        return orderDetailFlow
    }

    fun getServiceById(serviceId: Long): StateFlow<Service?> {
        val serviceFlow = MutableStateFlow<Service?>(null)
        viewModelScope.launch {
            serviceFlow.value = serviceDao.getServiceById(serviceId)
        }
        return serviceFlow
    }

    suspend fun deleteOrder(orderId: Long) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                orderDao.deleteOrderById(orderId)
            }
        } catch (e: Exception) {
            Log.d("TransactionViewModel", e.message.toString())
        }
    }


}

