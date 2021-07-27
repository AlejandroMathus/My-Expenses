package com.example.myexpenses.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses_table")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var expenseId: Long = 0L,

    @ColumnInfo(name = "expense_amount")
    var expenseAmount: Double,

    @ColumnInfo(name = "expense_category")
    var expenseCategory: String,

    @ColumnInfo(name = "expense_description")
    var expenseDescription: String?

)