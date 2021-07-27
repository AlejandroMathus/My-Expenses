package com.example.myexpenses.util

import java.text.DecimalFormat

class MoneyFormatter {
    private val twoDecimalDigitsFormat = DecimalFormat("#.##")

    fun format(value: Double?): String {
        value?.let {
            val result = twoDecimalDigitsFormat.format(value)
            return result
        }
        return ""
    }
}
