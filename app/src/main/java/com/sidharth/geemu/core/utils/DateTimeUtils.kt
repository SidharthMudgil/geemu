package com.sidharth.geemu.core.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
object DateTimeUtils {

    fun getOneYearRange(past: Boolean = false): String {
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val today = LocalDate.now()
        return when(past) {
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
}