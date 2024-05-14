package org.d3if3066.mylaundry.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service")
data class Service(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val price: Int,
)
