package com.example.myexpenses.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseDatabaseDao{
    @Insert
    fun insert(expense: Expense)

    @Update
    fun update(expense: Expense)

    @Delete
    fun delete(expense: Expense)

    @Query("SELECT * from expenses_table WHERE expenseId = :key")
    fun getExpenseWithId(key: Long): Expense

    @Query("SELECT * FROM expenses_table ORDER BY expenseId DESC")
    fun getAllExpenses(): LiveData<List<Expense>>
}