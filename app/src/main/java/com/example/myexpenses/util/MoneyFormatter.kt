package com.example.myexpenses.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class MoneyFormatter {
    private val twoDecimalDigitsFormat = DecimalFormat("#.##").apply {
        decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.US)
    }

    fun format(value: Double?): String {
        value?.let {
            val result = twoDecimalDigitsFormat.format(value)
            return result
        }
        return ""
    }
}
