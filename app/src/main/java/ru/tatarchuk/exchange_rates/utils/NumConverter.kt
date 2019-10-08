package ru.tatarchuk.exchange_rates.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object NumConverter {

    private fun getRateFormat(): ThreadLocal<DecimalFormat> = object : ThreadLocal<DecimalFormat>() {
        override fun initialValue(): DecimalFormat? {
            return DecimalFormat("0.0000", DecimalFormatSymbols(Locale.US))
        }
    }

    fun toRate(value: Float) = getRateFormat().get()?.format(value)

}