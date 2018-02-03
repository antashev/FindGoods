package ru.modulkassa.findgoods.ui.shared

import java.math.BigDecimal

fun BigDecimal.toCurrencyString(): String {
    return this.setScale(2, java.math.RoundingMode.HALF_UP).toString()
}