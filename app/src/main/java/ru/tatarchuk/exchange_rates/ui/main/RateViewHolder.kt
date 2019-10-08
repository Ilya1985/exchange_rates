package ru.tatarchuk.exchange_rates.ui.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.tatarchuk.exchange_rates.R
import ru.tatarchuk.exchange_rates.ui.main.model.CurrencyRate
import ru.tatarchuk.exchange_rates.utils.NumConverter
import ru.tatarchuk.exchange_rates.utils.ResourcesManager

class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val TAG = "<>${RateViewHolder::class.java.simpleName}"

    private val flag: ImageView = itemView.findViewById(R.id.flag)
    private val code: TextView = itemView.findViewById(R.id.char_code)
    private val name: TextView = itemView.findViewById(R.id.name)
    private val rate: TextView = itemView.findViewById(R.id.rate)

    fun bind(model: CurrencyRate) {
        val context = itemView.context
        flag.setImageDrawable(ResourcesManager.getDrawableByName(model.id, context))
        code.text = model.code
        name.text = ResourcesManager.getStringByName(model.id, context)
        rate.text = model.value?.let { v -> model.nominal?.let { n -> v/n } }?.let { NumConverter.toRate(it) }
    }
}