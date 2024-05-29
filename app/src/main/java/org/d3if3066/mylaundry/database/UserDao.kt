package org.d3if3066.mylaundry.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3066.mylaundry.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM user ORDER BY laundryName")
    suspend fun getAllUser(): List<User>
    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: Long): User?
    @Query("SELECT * FROM user WHERE email = :email")
    fun getUserByEmail(email: String): User?
    @Query("SELECT * FROM user WHERE signedIn = true")
    fun getSignedInUser(): User?

}