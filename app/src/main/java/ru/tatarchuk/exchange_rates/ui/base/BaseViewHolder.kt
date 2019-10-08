package ru.tatarchuk.exchange_rates.ui.base

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<M : BaseItem>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @LayoutRes
    abstract fun getLayoutResId(): Int

    abstract fun bind(model: M)
}