package org.d3if3066.mylaundry.ui.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.d3if3066.mylaundry.database.UserDao
import org.d3if3066.mylaundry.model.User

class LoginViewModel(private val userDao: UserDao) : ViewModel() {


    fun checkIsSignedIn(): Boolean {
        val user: User? = userDao.getSignedInUser()
        if (user != null) return true
        return false
    }

    fun login(email: String, password: String): Boolean {
        val user = userDao.getUserByEmail(email)
        if (user != null && user.password == password) {
            val assignedUser = User(user.id, user.laundryName, user.email, user.password, true)
            userDao.update(assignedUser)
            return true
        }
        return false
    }
}
