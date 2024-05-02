package org.d3if3066.mylaundry.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val laundryName: String,
    val email: String,
    val password: String,
    val signedIn: Boolean
)