package org.d3if3066.mylaundry.model

data class OrderDetail(
    val id: Long = 0L,
    val customerId: Long,
    val customerName: String,
    val customerPhone: String,
    val weight: Int,
    val serviceId: Long,
    val serviceName: String,
    val startDate: String,
    val endDate: String,
    val price: Int,
)
