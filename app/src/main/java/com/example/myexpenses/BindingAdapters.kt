package com.example.myexpenses

import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.example.myexpenses.database.Expense
import com.example.myexpenses.util.MoneyFormatter


@BindingAdapter("android:text")
fun EditText.bindAnyToString(value: Any?) {
    value?.let {
        if (value is Double) {
            val newValue = MoneyFormatter().format(value)
            Log.d("debug", "----------------------"+ newValue)
            if (text.toString() != newValue) {
                setText(newValue)
            }
        }
    }
}

@InverseBindingAdapter(attribute = "android:text")
fun EditText.getDoubleFromBinding(): Double? {
    val result = text.toString()
    return try {
        if (result.endsWith(".")) return null

        result.toDouble()
    } catch(e: NumberFormatException) {
        null
    }
}

@BindingAdapter("expenseFormatted")
fun TextView.setExpenseFormatted(item: Expense?) {
    item?.let {
        text = item.expenseAmount.toString()
    }
}


@BindingAdapter("expenseCategoryString")
fun TextView.setExpenseCategoryString(item: Expense?) {
    item?.let {
        text = item.expenseCategory
    }
}

@BindingAdapter("expenseDescriptionString")
fun TextView.setExpenseDescriptionString(item: Expense?) {
    item?.let {
        text = item.expenseDescription
    }
}