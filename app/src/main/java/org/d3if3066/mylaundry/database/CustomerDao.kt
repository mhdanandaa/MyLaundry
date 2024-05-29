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
    @Query("SELECT * FROM customer WHERE LOWER(name) = LOWER(:name)")
    suspend fun getCustomerByName(name:String): Customer?

    // Add this function definition:
    @Query("SELECT * FROM customer WHERE id = :customerId")
    suspend fun getCustomerById(customerId: Long): Customer?

    @Query("DELETE FROM `customer` WHERE id = :id")
    suspend fun deleteCustomerById(id: Long):Int
}