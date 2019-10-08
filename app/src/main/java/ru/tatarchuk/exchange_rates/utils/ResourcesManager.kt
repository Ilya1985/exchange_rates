package ru.tatarchuk.exchange_rates.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import ru.tatarchuk.exchange_rates.R

object ResourcesManager {

    private fun getDrawableResId(name: String, context: Context) =
        context.resources.getIdentifier(name.substring(0, 6).toLowerCase(), "drawable", context.packageName)

    fun getDrawableByName(name: String, context: Context): Drawable? {
        return try {
            AppCompatResources.getDrawable(context, getDrawableResId(name, context))
        } catch (e: Resources.NotFoundException) {
            AppCompatResources.getDrawable(context, R.drawable.na)
        }
    }

    private fun getStringResId(name: String, context: Context): Int {
        return context.resources.getIdentifier(name.substring(0, 6).toLowerCase(), "string", context.packageName)
    }


    fun getStringByName(name: String, context: Context): String =
        try {
            context.resources.getString(getStringResId(name, context))
        } catch (e: Resources.NotFoundException) {
            ""
        }

}