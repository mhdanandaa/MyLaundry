package org.d3if3066.mylaundry.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3066.mylaundry.model.Customer
import org.d3if3066.mylaundry.model.Order
import org.d3if3066.mylaundry.model.Service
import org.d3if3066.mylaundry.model.User

@Database(entities = [User::class,Customer::class,Service::class,Order::class], version = 1, exportSchema = true)
abstract class MyLaundryDb : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val serviceDao: ServiceDao
    abstract val orderDao: OrderDao
    abstract val customerDao: CustomerDao

    companion object {

        @Volatile
        private var INSTANCE: MyLaundryDb? = null
        fun getInstance(context: Context, resetDatabase: Boolean): MyLaundryDb {
            if (resetDatabase && INSTANCE == null) {
                (context.getDatabasePath("mylaundry.db")).delete()
            }
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context,MyLaundryDb::class.java,"mylaundry.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE as MyLaundryDb
        }
        fun getInstance(context: Context): MyLaundryDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyLaundryDb::class.java,
                        "mylaundry.db"
                    ).allowMainThreadQueries().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}