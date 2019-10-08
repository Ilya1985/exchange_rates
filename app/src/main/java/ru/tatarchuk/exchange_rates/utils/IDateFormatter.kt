package ru.tatarchuk.exchange_rates.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object IDateFormatter {

    val requestDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    private val responseDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    fun parseResponseDate(data: String?): LocalDate = LocalDate.parse(data, responseDateFormat)

}