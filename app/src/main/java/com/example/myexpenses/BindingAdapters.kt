package com.example.myexpenses

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.example.myexpenses.util.MoneyFormatter


@BindingAdapter("android:text")
fun EditText.bindAnyToString(value: Any?) {
    value?.let {
        if (value is Double) {
            val newValue = MoneyFormatter().format(value)
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
