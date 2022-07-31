package com.siuliano.emovies.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"

    private fun getTomorrowDate(): Date {
        return Calendar.getInstance(Locale.getDefault()).apply {
            add(Calendar.DAY_OF_YEAR, 1)
        }.time
    }

    fun getTomorrowDate(format: String = DEFAULT_DATE_FORMAT): String =
        SimpleDateFormat(format, Locale.getDefault()).format(getTomorrowDate())

}

object StringUtils {
    fun String.extractNumbersToInt() = this.filter { character -> character.isDigit() }.toIntOrNull() ?: 0
}

object DoubleUtils {
    fun Double.toStringWithDecimals(decimalPlaces: Int = 2) = String.format("%.$decimalPlaces", this)
}