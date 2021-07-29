package com.example.myexpenses.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses_table")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Long = 0L,

    @ColumnInfo(name = "expense_amount")
    val expenseAmount: Double,

    @ColumnInfo(name = "expense_category")
    val expenseCategory: String,

    @ColumnInfo(name = "expense_description")
    val expenseDescription: String?

)