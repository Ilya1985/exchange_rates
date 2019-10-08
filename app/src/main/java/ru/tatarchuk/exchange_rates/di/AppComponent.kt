package ru.tatarchuk.exchange_rates.di

import dagger.Component
import ru.tatarchuk.exchange_rates.ui.main.MainRepo
import ru.tatarchuk.exchange_rates.ui.main.MainViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RestModule::class])
interface AppComponent {

    fun inject(repo: MainRepo)

    //    ViewModel
    fun inject(model: MainViewModel)
}
