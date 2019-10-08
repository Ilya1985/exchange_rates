package ru.tatarchuk.exchange_rates.ui.base

import androidx.annotation.LayoutRes

abstract class BaseItem {

    @LayoutRes
    abstract fun getLayoutResId(): Int
}