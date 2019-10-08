package ru.tatarchuk.exchange_rates.ui.main.model

import androidx.room.ColumnInfo

data class CurrencyRate(
    @ColumnInfo(name = "currencyId")
    var id: String,
    @ColumnInfo(name = "CharCode")
    var code: String?,
    @ColumnInfo(name = "nominal")
    var nominal: Float?,
    @ColumnInfo(name = "value")
    var value: Float?
)