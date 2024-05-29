package org.d3if3066.mylaundry.ui.screen.service

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.d3if3066.mylaundry.database.ServiceDao
import org.d3if3066.mylaundry.model.Service

class ServiceViewModel(private val serviceDao: ServiceDao) : ViewModel() {
    val serviceList: StateFlow<List<Service>> = serviceDao.getAllService().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    suspend fun createService(name:String,price:Int):Boolean{
        try {
            serviceDao.insert(Service(name = name, price = price))
            return true
        } catch (e:Exception){
            Log.d("service",e.message!!)
        }
        return false
    }
    suspend fun deleteService(serviceId: Long) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                serviceDao.deleteServiceById(serviceId)
            }
        } catch (e: Exception) {
            Log.d("ServiceViewModel", e.message.toString())
        }
    }


}