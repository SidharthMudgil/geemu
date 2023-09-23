package com.sidharth.geemu.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateTimeUtils {

    fun getOneYearRange(past: Boolean = false): String {
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val today = LocalDate.now()
        return when (past) {
            true -> {
                val prev = today.minusYears(1)
                "${prev.format(format)},${today.format(format)}"
            }

            else -> {
                val later = today.plusYears(1)
                "${today.format(format)},${later.format(format)}"
            }
        }
    }

    fun String.toPrettyFormat(
        inputFormat: String = "yyyy-MM-dd",
        outputFormat: String = "dd MMM yyyy",
    ): String {
        val inputFormatter = DateTimeFormatter.ofPattern(inputFormat)
        val date = LocalDate.parse(this, inputFormatter)
        return DateTimeFormatter.ofPattern(outputFormat).format(date)
    }
}