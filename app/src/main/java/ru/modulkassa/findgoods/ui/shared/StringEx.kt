package ru.modulkassa.findgoods.ui.shared

import java.math.BigDecimal

fun String.toBigDecimal(): BigDecimal {
    return try {
        BigDecimal(this.toString()).setScale(2, BigDecimal.ROUND_HALF_UP)
    } catch (e: NumberFormatException) {
        BigDecimal.ZERO
    }
}