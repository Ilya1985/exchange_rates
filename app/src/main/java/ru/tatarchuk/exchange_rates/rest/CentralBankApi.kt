package ru.tatarchuk.exchange_rates.rest

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.tatarchuk.exchange_rates.rest.response.ValCurs

interface CentralBankApi {

    @GET("XML_daily.asp?")
    fun getCurrency(): Observable<ValCurs>

    @GET("XML_daily.asp")
    fun getCurrencyByDate(@QueryMap map: Map<String, String>): Observable<ValCurs>

}