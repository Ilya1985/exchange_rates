package ru.tatarchuk.exchange_rates.utils

import android.preference.PreferenceManager
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import ru.tatarchuk.exchange_rates.IApplication

object ISharePref {

    private const val KEY_LAST_UPDATE = "last_update"
    private val preference = PreferenceManager.getDefaultSharedPreferences(IApplication.instance)

    var timestamp: LocalDateTime
        get() = LocalDateTime.ofEpochSecond(
            preference.getLong(
                KEY_LAST_UPDATE,
                LocalDateTime.MIN.toEpochSecond(ZoneOffset.UTC)
            ), 0, ZoneOffset.UTC
        )
        set(value) {
            preference.edit().putLong(KEY_LAST_UPDATE, value.toEpochSecond(ZoneOffset.UTC)).apply()
        }
}