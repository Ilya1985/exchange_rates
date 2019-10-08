package ru.tatarchuk.exchange_rates.di

import dagger.Module
import dagger.Provides
import ru.tatarchuk.exchange_rates.rest.CentralBankApi
import ru.tatarchuk.exchange_rates.rest.CentralBankClient
import javax.inject.Singleton

@Module
class RestModule {

    @Provides
    @Singleton
    fun provideCentralBankClient() = CentralBankClient()

    @Provides
    @Singleton
    fun provideCentralBankApi(client: CentralBankClient) = client.createService(CentralBankApi::class.java)
}