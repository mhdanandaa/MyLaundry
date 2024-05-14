package org.d3if3066.mylaundry.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3066.mylaundry.model.Customer

@Dao
interface CustomerDao {

    @Insert
    suspend fun insert(customer: Customer)

    @Update
    suspend fun update(customer: Customer)

    @Query("SELECT * FROM customer")
    fun getAllCustomer(): Flow<List<Customer>>
    @Query("SELECT * FROM customer WHERE name = :name")
    suspend fun getCustomerByName(name:String): Customer?
}