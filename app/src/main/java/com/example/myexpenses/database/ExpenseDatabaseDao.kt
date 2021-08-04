package com.example.myexpenses.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseDatabaseDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("SELECT * from expenses_table WHERE expenseId = :key")
    fun getExpenseWithId(key: Long): LiveData<Expense>

    @Query("SELECT * FROM expenses_table ORDER BY expenseId DESC")
    fun getAllExpenses(): LiveData<List<Expense>>
}