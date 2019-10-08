package ru.tatarchuk.exchange_rates.ui.main.model

import org.threeten.bp.LocalDate

data class DailyRates(val data: List<CurrencyRate>, val date: LocalDate)