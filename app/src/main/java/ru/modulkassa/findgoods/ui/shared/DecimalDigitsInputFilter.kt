package ru.modulkassa.findgoods.ui.shared

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern


class DecimalDigitsInputFilter(digitsBeforeZero: Int, digitsAfterZero: Int) : InputFilter {

    private val pattern: Pattern

    init {
        val decimalSeparator = '.' // DecimalFormatSymbols.getInstance().decimalSeparator
        val filter = "[0-9]{0,$digitsBeforeZero}+((\\$decimalSeparator[0-9]{0,$digitsAfterZero})?)||(\\$decimalSeparator)?"
        pattern = Pattern.compile(filter)
    }

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int,
                        dend: Int): CharSequence? {
        if (source.isEmpty()) {
            return null
        }
        var destText = dest.toString()
        destText = destText.replaceRange(dstart, dend, source.subSequence(start, end))
        val matcher = pattern.matcher(destText)
        if (!matcher.matches()) {
            return ""
        }
        if (destText.startsWith("00")) {
            return ""
        }
        return null
    }

}