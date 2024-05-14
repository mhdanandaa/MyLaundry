package org.d3if3066.mylaundry.ui.screen.register

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3066.mylaundry.database.UserDao
import org.d3if3066.mylaundry.model.User

class RegisterViewModel(private val dao: UserDao) : ViewModel(){
    suspend fun register(laundryName:String,email:String,password:String):Boolean{
        val user = dao.getUserByEmail(email)
        if (user == null) {
            val user = User(
                laundryName = laundryName,
                email   = email,
                password = password,
                signedIn = false
            )

            viewModelScope.launch(Dispatchers.IO) {
                dao.insert(user)
            }
            return true
        }
        return false
    }
}