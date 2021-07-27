package com.example.myexpenses.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Expense::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract val expenseDatabaseDao: ExpenseDatabaseDao

    companion object {
        // Keep a reference to the database
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        // If a database has already been retrieved, returns the previous database.
        // Otherwise, creates a new database.
        fun getInstance(context: Context): ExpenseDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExpenseDatabase::class.java,
                        "sad"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}