package ru.tatarchuk.exchange_rates.room.converter

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate

class DateConverter {

    @TypeConverter
    fun toLong(date: LocalDate?) = date?.toEpochDay()

    @TypeConverter
    fun toDate(long: Long?): LocalDate? = LocalDate.ofEpochDay(long ?: 0)
}