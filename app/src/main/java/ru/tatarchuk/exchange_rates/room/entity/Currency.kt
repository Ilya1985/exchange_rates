package ru.tatarchuk.exchange_rates.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class Currency(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "CharCode")
    var charCode: String?,

    @ColumnInfo(name = "NumCode")
    var numCode: String?
)





