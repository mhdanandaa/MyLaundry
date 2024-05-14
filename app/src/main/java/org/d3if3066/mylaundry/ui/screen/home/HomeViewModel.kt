package org.d3if3066.mylaundry.ui.screen.home

import androidx.lifecycle.ViewModel
import org.d3if3066.mylaundry.database.UserDao
import org.d3if3066.mylaundry.model.User

class HomeViewModel(private val userDao: UserDao) : ViewModel() {
    suspend fun getSignedInUser():User?{
        val user: User? = userDao.getSignedInUser()
        if (user != null) return user
        return null
    }
}