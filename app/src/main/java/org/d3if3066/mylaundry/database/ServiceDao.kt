package org.d3if3066.mylaundry.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3066.mylaundry.model.Customer
import org.d3if3066.mylaundry.model.Service

@Dao
interface ServiceDao {

    @Insert
    suspend fun insert(service: Service)

    @Update
    suspend fun update(service: Service)

    @Query("SELECT * FROM service WHERE name = :name")
    suspend fun getServicerByName(name:String): Service?
    @Query("SELECT * FROM service ORDER BY name")
    fun getAllService(): Flow<List<Service>>

    @Query("SELECT * FROM service WHERE id = :serviceId")
    suspend fun getServiceById(serviceId: Long): Service?
}