package ru.tatarchuk.exchange_rates.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.tatarchuk.exchange_rates.R

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun getLayoutResId(): Int

    abstract fun createFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())

        supportFragmentManager.findFragmentById(R.id.fragment_container)?.let { }
            ?: supportFragmentManager.beginTransaction().add(R.id.fragment_container, createFragment()).commit()
    }
}