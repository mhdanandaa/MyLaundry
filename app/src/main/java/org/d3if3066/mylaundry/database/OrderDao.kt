package org.d3if3066.mylaundry.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3066.mylaundry.model.Order
import org.d3if3066.mylaundry.model.OrderDetail


@Dao
interface OrderDao {

    @Insert
    suspend fun insert(order: Order)

    @Update
    suspend fun update(order: Order)

    @Query("DELETE FROM `order` WHERE id = :id")
    suspend fun deleteOrderById(id: Long): Int

    @Query("SELECT * FROM `order`")
    fun getOrder(): Flow<List<Order>>

    @Query("SELECT * FROM `order` WHERE id = :id")
    suspend fun getOrderById(id: Long): Order?

    @Query(
        "SELECT " +
                "`order`.id AS 'id'," +
                "`order`.customerId AS 'customerId'," +
                "`order`.weight AS 'weight'," +
                "`order`.serviceId AS 'serviceId'," +
                "`order`.startDate AS 'startDate'," +
                "`order`.endDate AS 'endDate'," +
                "`order`.price AS 'price'," +
                "service.name AS 'serviceName'," +
                "customer.name AS 'customerName',  " +
                "customer.phone AS 'customerPhone'  " +
                "FROM `order` " +
                "INNER JOIN service ON service.id = `order`.serviceId " +
                "INNER JOIN customer ON customer.id = `order`.customerId " +
                "ORDER BY `order`.startDate DESC"
    )
    fun getAllOrderDetail(): Flow<List<OrderDetail>>

    @Query(
        "SELECT " +
                "`order`.id AS 'id'," +
                "`order`.customerId AS 'customerId'," +
                "`order`.weight AS 'weight'," +
                "`order`.serviceId AS 'serviceId'," +
                "`order`.startDate AS 'startDate'," +
                "`order`.endDate AS 'endDate'," +
                "`order`.price AS 'price'," +
                "service.name AS 'serviceName'," +
                "customer.name AS 'customerName',  " +
                "customer.phone AS 'customerPhone'  " +
                "FROM `order` " +
                "INNER JOIN service ON service.id = `order`.serviceId " +
                "INNER JOIN customer ON customer.id = `order`.customerId WHERE `order`.id = :id"
    )
    fun getOrderDetailById(id: Long): OrderDetail?

    @Query(
        "SELECT " +
                "`order`.id AS 'id'," +
                "`order`.customerId AS 'customerId'," +
                "`order`.weight AS 'weight'," +
                "`order`.serviceId AS 'serviceId'," +
                "`order`.startDate AS 'startDate'," +
                "`order`.endDate AS 'endDate'," +
                "`order`.price AS 'price'," +
                "service.name AS 'serviceName'," +
                "customer.name AS 'customerName',  " +
                "customer.phone AS 'customerPhone'  " +
                "FROM `order` " +
                "INNER JOIN service ON service.id = `order`.serviceId " +
                "INNER JOIN customer ON customer.id = `order`.customerId "
                + "WHERE " +
                "strftime('%Y', `order`.startDate) = strftime('%Y', 'now')" +
                "AND strftime('%m', `order`.startDate) = strftime('%m', 'now')"
    )
    fun getOrderDetailByMonthAndYear(): Flow<List<OrderDetail>>

    @Query(
        "SELECT " +
                "SUM(`order`.price) AS 'totalRevenue'  " +
                "FROM `order` " +
                "INNER JOIN service ON service.id = `order`.serviceId " +
                "INNER JOIN customer ON customer.id = `order`.customerId "
                + "WHERE " +
                "strftime('%Y', `order`.startDate) = strftime('%Y', 'now')" +
                "AND strftime('%m', `order`.startDate) = strftime('%m', 'now')"
    )
    fun getSumRevenueCurrentMonthAndYear(): Int
}