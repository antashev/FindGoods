package ru.modulkassa.findgoods.ui.shared

import android.text.Editable
import java.math.BigDecimal

fun Editable.toBigDecimal(): BigDecimal {
    return try {
        BigDecimal(this.toString()).setScale(2)
    } catch (e: NumberFormatException) {
        BigDecimal.ZERO
    }
}