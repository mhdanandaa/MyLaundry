package org.d3if3066.mylaundry.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val phone: String? = null,
)