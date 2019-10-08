package ru.tatarchuk.exchange_rates.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.tatarchuk.exchange_rates.room.converter.DateConverter
import ru.tatarchuk.exchange_rates.room.dao.BaseDao
import ru.tatarchuk.exchange_rates.room.entity.Currency
import ru.tatarchuk.exchange_rates.room.entity.DailyExchangeRate
import ru.tatarchuk.exchange_rates.room.entity.Rate

@Database(
    entities = [DailyExchangeRate::class, Currency::class, Rate::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class IDataBase : RoomDatabase() {
    abstract fun getDao(): BaseDao
}