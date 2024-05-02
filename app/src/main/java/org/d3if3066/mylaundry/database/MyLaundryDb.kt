package org.d3if3066.mylaundry.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3066.mylaundry.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class MyLaundryDb : RoomDatabase() {

    abstract val dao: UserDao

    companion object {

        @Volatile
        private var INSTANCE: MyLaundryDb? = null

        fun getInstance(context: Context): MyLaundryDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyLaundryDb::class.java,
                        "mylaundry.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}