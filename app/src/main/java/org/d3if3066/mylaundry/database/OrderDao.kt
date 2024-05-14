package org.d3if3066.mylaundry.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import org.d3if3066.mylaundry.model.Order


@Dao
interface OrderDao {

    @Insert
    suspend fun insert(order: Order)

    @Update
    suspend fun update(order: Order)
}