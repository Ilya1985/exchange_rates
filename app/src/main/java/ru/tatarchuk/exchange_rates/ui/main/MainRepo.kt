package ru.tatarchuk.exchange_rates.ui.main

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDate
import ru.tatarchuk.exchange_rates.IApplication
import ru.tatarchuk.exchange_rates.rest.CentralBankApi
import ru.tatarchuk.exchange_rates.rest.request.ExchangeRatesRequest
import ru.tatarchuk.exchange_rates.rest.response.ValCurs
import ru.tatarchuk.exchange_rates.room.dao.BaseDao
import ru.tatarchuk.exchange_rates.room.entity.Currency
import ru.tatarchuk.exchange_rates.room.entity.DailyExchangeRate
import ru.tatarchuk.exchange_rates.ui.main.model.CurrencyRate
import ru.tatarchuk.exchange_rates.ui.main.model.DailyRates
import ru.tatarchuk.exchange_rates.utils.IDateFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainRepo {

    companion object {
        private val TAG = "<>${MainRepo::class.java.simpleName}"
    }

    @Inject
    lateinit var api: CentralBankApi
    @Inject
    lateinit var dao: BaseDao

    init {
        IApplication.component.inject(this)
    }

    // Загрузка данных с сервера на ближайшую дату
    fun loadActualData(): Observable<DailyRates> = api
        .getCurrency()
        .subscribeOn(Schedulers.io())
        //OnErrorNotImplementedException Too many follow-up requests: 21
        .retryWhen { it.take(3).delay(1, TimeUnit.SECONDS) }
        .flatMap { saveValCurs(it, null) }
        .observeOn(AndroidSchedulers.mainThread())

    // Загрузка данных с сервера на указанную дату
    private fun loadRatesByDate(date: LocalDate) = api
        .getCurrencyByDate(ExchangeRatesRequest(date).toMap())
        .subscribeOn(Schedulers.io())
        .doOnSubscribe { Log.i(TAG, "data download on  ${date.format(IDateFormatter.requestDateFormat)}") }
        .retryWhen { it.take(3).delay(1, TimeUnit.SECONDS) }
        .flatMap { saveValCurs(it, date) }

    // Получение данных на ближайшую дату из БД
    fun getLastData(): Single<DailyRates> = Single.zip(
        dao.getLastDate(),
        dao.getLastData(),
        BiFunction<Long, List<CurrencyRate>, DailyRates> { date, currencies ->
            DailyRates(currencies, LocalDate.ofEpochDay(date))
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    // Если данных нет в БД, загружает с сервера
    fun getByDate(date: LocalDate): Observable<DailyRates> = dao
        .getDailyRate(date.toEpochDay())
        .subscribeOn(Schedulers.io())
        .toObservable()
        .flatMap {
            if (it.isEmpty()) {
                Log.i(TAG, "no data in the database on ${date.format(IDateFormatter.requestDateFormat)}")
                loadRatesByDate(date)
            } else {
                Log.i(TAG, "data on ${date.format(IDateFormatter.requestDateFormat)} is in the database")
                Observable.just(DailyRates(it, date))
            }
        }
        .observeOn(AndroidSchedulers.mainThread())


    // Метод принимает ValCurs, добавлюяет в БД валюту, курсы валют на заданную дату
    // и возвращает Observable<List<CurrencyRate>> для передачи во фрагмент
    private fun saveValCurs(valCurs: ValCurs, date: LocalDate?): Observable<DailyRates> {
        val d: LocalDate = if (date != null) date else {
            val now = LocalDate.now()
            val cursDate = IDateFormatter.parseResponseDate(valCurs.date)
            if (cursDate < now) now else cursDate
        }


        val currencies = mutableListOf<Currency>()
        val rates = mutableListOf<DailyExchangeRate>()
        val result = mutableListOf<CurrencyRate>()
        valCurs.currencyList?.forEach {
            currencies.add(Currency(it.id ?: "", it.charCode, it.numCode))
            rates.add(
                DailyExchangeRate(
                    it.id ?: "", d,
                    it.value?.replace(',', '.')?.toFloat(),
                    it.nominal
                )
            )
            result.add(
                CurrencyRate(
                    it.id ?: "",
                    it.charCode,
                    it.nominal,
                    it.value?.replace(',', '.')?.toFloat() ?: 0f
                )
            )
        }

        Log.i(
            TAG,
            "load OK!!! date request ${date?.format(IDateFormatter.requestDateFormat)} count = ${valCurs.currencyList?.size}"
        )

        // Сохранить в БД данные на указанную дату
        dao.insertCurrency(currencies)
        // Если появилась новая валюта, добавит в бд
        dao.insertDailyRates(rates)
        // Возвращает данные в формате для передачи фрагменту
        return Observable.just(DailyRates(result, d))
    }
}