package ru.tatarchuk.exchange_rates.rest.request

import org.threeten.bp.LocalDate
import ru.tatarchuk.exchange_rates.utils.IDateFormatter

class ExchangeRatesRequest(date: LocalDate) : Request() {

    private val dateTitle = "date_req"

    var stringDate: String = date.format(IDateFormatter.requestDateFormat)

    override fun onMapCreate(map: MutableMap<String, String>) {
        map[dateTitle] = stringDate
    }
}