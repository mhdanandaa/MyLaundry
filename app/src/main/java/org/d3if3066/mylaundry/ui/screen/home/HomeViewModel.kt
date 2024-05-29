package org.d3if3066.mylaundry.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3066.mylaundry.database.OrderDao
import org.d3if3066.mylaundry.database.UserDao
import org.d3if3066.mylaundry.model.OrderDetail
import org.d3if3066.mylaundry.model.User
import java.util.Calendar

class HomeViewModel(private val userDao: UserDao,private val orderDao: OrderDao) : ViewModel() {

    suspend fun getSignedInUser():User?{
        val user: User? = userDao.getSignedInUser()
        if (user != null) return user
        return null
    }
    suspend fun getCurrentMonthRevenue():Int{
        return orderDao.getSumRevenueCurrentMonthAndYear()
    }
}