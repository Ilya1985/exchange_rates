package ru.tatarchuk.exchange_rates.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import ru.tatarchuk.exchange_rates.room.entity.Currency
import ru.tatarchuk.exchange_rates.room.entity.DailyExchangeRate
import ru.tatarchuk.exchange_rates.room.entity.Rate
import ru.tatarchuk.exchange_rates.ui.main.model.CurrencyRate

@Dao
interface BaseDao {

    /**Currency*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCurrency(currency: Currency)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCurrency(currency: List<Currency>)

    @Query("DELETE FROM currency WHERE id = :id")
    fun deleteCurrency(id: String)

    @Query("SELECT * FROM currency WHERE id = :id")
    fun getCurrency(id: String): Single<List<Currency>>

    /**DailyRate*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDailyRates(data: List<DailyExchangeRate>): List<Long>?

    @Query("SELECT * FROM DailyExchangeRates WHERE date = :date")
    fun getRateByDate(date: Long): Single<List<DailyExchangeRate>>

    @Query("SELECT MAX(date) FROM DailyExchangeRates")
    fun getLastDate(): Single<Long>

    /**Rate*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRate(currencyRate: Rate): Long?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRate(rateList: List<Rate>): List<Long>?

    @Query("DELETE FROM Rate WHERE currencyId = :id")
    fun deleteRate(id: String)

    /**Query from multiple tables*/

    @Query("SELECT currencyId, CharCode, nominal, value FROM DailyExchangeRates, Currency WHERE id = currencyId AND date = :date")
    fun getDailyRate(date: Long): Single<List<CurrencyRate>>

    @Query("SELECT currencyId, CharCode, nominal, value FROM DailyExchangeRates, Currency WHERE id = currencyId AND date = (SELECT max(date) FROM DailyExchangeRates)")
    fun getLastData(): Single<List<CurrencyRate>>
}