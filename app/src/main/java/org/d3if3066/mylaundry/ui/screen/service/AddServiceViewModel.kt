package org.d3if3066.mylaundry.ui.screen.service

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.d3if3066.mylaundry.database.ServiceDao
import org.d3if3066.mylaundry.model.Service

class AddServiceViewModel(private val serviceDao: ServiceDao) : ViewModel() {
    suspend fun createService(name:String,price:Int):Boolean{
        try {
            serviceDao.insert(Service(name = name, price = price))
            return true
        } catch (e:Exception){
            Log.d("service",e.message!!)
        }
        return false
    }
}