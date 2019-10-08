package ru.tatarchuk.exchange_rates.rest

class CentralBankClient : RestClient() {

    override fun baseUrl() = "http://www.cbr.ru/scripts/"
}