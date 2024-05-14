package org.d3if3066.mylaundry.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "order")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val customerId: Long,
    val weight: Int,
    val serviceId: Long,
    val startDate: String,
    val endDate: String,
    val price: Int,
)