package ru.modulkassa.findgoods.ui.shared

import java.math.BigDecimal

fun BigDecimal.toCurrencyString(): String {
    return this.setScale(2, BigDecimal.ROUND_HALF_UP).toString()
}