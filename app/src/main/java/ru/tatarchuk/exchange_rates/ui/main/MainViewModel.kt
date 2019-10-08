package ru.tatarchuk.exchange_rates.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import ru.tatarchuk.exchange_rates.IApplication
import ru.tatarchuk.exchange_rates.ui.main.model.DailyRates
import ru.tatarchuk.exchange_rates.utils.Const.FIRST_DATE
import javax.inject.Inject

class MainViewModel : ViewModel() {

    companion object {
        private val TAG = "<>${MainViewModel::class.java.simpleName}"
    }

    val data = MutableLiveData<DailyRates>()
    val progress = MutableLiveData(false)
    private val disposable = CompositeDisposable()

    var maxDate: OffsetDateTime = OffsetDateTime.now()

    @Inject
    lateinit var repositories: MainRepo

    init {
        Log.i(TAG, "init")
        IApplication.component.inject(this)
        disposable.addAll(
            // При запуске запрашивает максимально свежие данные из БД
            repositories
                .getLastData()
                .subscribe({ data.postValue(it) }, { Log.i(TAG, "database is empty", it) }),
            // И загружает актуальные двнные с севера
            repositories
                .loadActualData()
                .doOnSubscribe { progress.postValue(true) }
                .doFinally { progress.postValue(false) }
                .doOnNext { setMaxDate(it) }
                .subscribe({ data.postValue(it) }, { Log.i(TAG, "failed update", it) })
        )
    }

    fun getByDate(date: LocalDate) {
        val d = if (date < FIRST_DATE.toLocalDate()) FIRST_DATE.toLocalDate() else date
        disposable.addAll(repositories
            .getByDate(d)
            .doOnSubscribe { progress.postValue(true) }
            .doFinally { progress.postValue(false) }
            .doOnNext { setMaxDate(it) }
            .subscribe({ data.postValue(it) }, { Log.i(TAG, "failed update", it) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        Log.i(TAG, "onCleared")
    }

    private fun setMaxDate(it: DailyRates) {
        if (it.date.toEpochDay() > maxDate.toLocalDate().toEpochDay()) maxDate =
            OffsetDateTime.of(it.date, LocalTime.of(0, 0), ZoneOffset.UTC)
    }

    // fun currentDate() = if (data.value?.date != null) data.value?.date else LocalDate.now()

    fun currentDate(): LocalDate = data.value?.date.let { return@let it?.minusMonths(1) } ?: LocalDate.now()
}