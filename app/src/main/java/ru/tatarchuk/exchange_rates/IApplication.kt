package ru.tatarchuk.exchange_rates

import android.app.Application
import android.util.Log
import com.jakewharton.threetenabp.AndroidThreeTen
import ru.tatarchuk.exchange_rates.di.AppComponent
import ru.tatarchuk.exchange_rates.di.AppModule
import ru.tatarchuk.exchange_rates.di.DaggerAppComponent

class IApplication : Application() {

    companion object {
        lateinit var instance: IApplication
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        instance = this
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}