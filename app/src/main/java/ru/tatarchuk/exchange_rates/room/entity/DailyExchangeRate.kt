package ru.tatarchuk.exchange_rates.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.TypeConverters
import org.threeten.bp.LocalDate
import ru.tatarchuk.exchange_rates.room.converter.DateConverter

@Entity(
    tableName = "DailyExchangeRates",
    primaryKeys = ["currencyId", "date"],
    foreignKeys = [ForeignKey(
        entity = Currency::class,
        parentColumns = ["id"],
        childColumns = ["currencyId"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class DailyExchangeRate(

    @ColumnInfo(name = "currencyId")
    var currencyId: String,

    @ColumnInfo(name = "date")
    @TypeConverters(DateConverter::class)
    var date: LocalDate,

    @ColumnInfo(name = "value")
    var value: Float?,

    @ColumnInfo(name = "nominal")
    var nominal: Float?
)