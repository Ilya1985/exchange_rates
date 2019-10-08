package ru.tatarchuk.exchange_rates.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.tatarchuk.exchange_rates.R
import ru.tatarchuk.exchange_rates.ui.main.model.CurrencyRate

class MainAdapter : RecyclerView.Adapter<RateViewHolder>() {

    var data = listOf<CurrencyRate>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_rate, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onViewRecycled(holder: RateViewHolder) {
        super.onViewRecycled(holder)
    }
}