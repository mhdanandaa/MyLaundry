package org.d3if3066.mylaundry.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import org.d3if3066.mylaundry.database.OrderDao
import org.d3if3066.mylaundry.database.UserDao
import org.d3if3066.mylaundry.model.User

class HomeViewModel(private val userDao: UserDao,private val orderDao: OrderDao) : ViewModel() {

    fun getSignedInUser():User?{
        val user: User? = userDao.getSignedInUser()

        if (user != null) return user
        return null
    }
    fun getCurrentMonthRevenue():Int{
        Log.d("alur",orderDao.getSumRevenueCurrentMonthAndYear().toString())
        return orderDao.getSumRevenueCurrentMonthAndYear()
    }
}