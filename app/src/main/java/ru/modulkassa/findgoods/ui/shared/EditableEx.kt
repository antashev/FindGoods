package ru.modulkassa.findgoods.ui.shared

import android.text.Editable
import java.math.BigDecimal

fun Editable.toBigDecimal(): BigDecimal {
    return try {
        BigDecimal(this.toString()).setScale(2, BigDecimal.ROUND_HALF_UP)
    } catch (e: NumberFormatException) {
        BigDecimal.ZERO
    }
}