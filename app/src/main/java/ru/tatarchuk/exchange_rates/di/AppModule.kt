package ru.tatarchuk.exchange_rates.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.tatarchuk.exchange_rates.room.IDataBase
import ru.tatarchuk.exchange_rates.ui.main.MainRepo
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideDataBase(context: Context) = Room.databaseBuilder(context, IDataBase::class.java, "database").build()

    @Provides
    @Singleton
    fun provideDao(db: IDataBase) = db.getDao()

    @Provides
    fun provideMainRepo() = MainRepo()

}