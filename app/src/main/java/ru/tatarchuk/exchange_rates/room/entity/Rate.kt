package ru.tatarchuk.exchange_rates.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "Rate",
    primaryKeys = ["currencyId", "currentDate"],
    foreignKeys = [ForeignKey(
        entity = Currency::class,
        parentColumns = ["id"],
        childColumns = ["currencyId"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)

data class Rate(

    @ColumnInfo(name = "currencyId")
    var currencyId: String,

    @ColumnInfo(name = "currentDate")
    var date: Int,

    @ColumnInfo(name = "value")
    var value: Float?
)