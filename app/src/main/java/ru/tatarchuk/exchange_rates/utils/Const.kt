package ru.tatarchuk.exchange_rates.utils

import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

object Const {

    val rur = "/u20BD"

    val FIRST_DATE = LocalDateTime.of(1998, 1, 1, 0, 0)
    val FIRST_DATE_MILLIS = OffsetDateTime.of(FIRST_DATE, ZoneOffset.UTC).toInstant().toEpochMilli()
}