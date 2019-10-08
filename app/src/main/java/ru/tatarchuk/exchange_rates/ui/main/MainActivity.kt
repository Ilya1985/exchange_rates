package ru.tatarchuk.exchange_rates.ui.main

import android.os.Bundle
import ru.tatarchuk.exchange_rates.ui.base.BaseActivity
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import android.view.View
import androidx.appcompat.widget.Toolbar


class MainActivity : BaseActivity() {

    override fun getLayoutResId() = ru.tatarchuk.exchange_rates.R.layout.activity_main

    override fun createFragment() = MainFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        centerTitle()
    }

    private fun centerTitle() {
        val textViews = arrayListOf<View>()

        window.decorView.findViewsWithText(textViews, title, View.FIND_VIEWS_WITH_TEXT)

        if (textViews.size > 0) {
            var appCompatTextView: AppCompatTextView? = null
            if (textViews.size == 1) {
                appCompatTextView = textViews[0] as AppCompatTextView
            } else {
                for (v in textViews) {
                    if (v.parent is Toolbar) {
                        appCompatTextView = v as AppCompatTextView
                        break
                    }
                }
            }

            if (appCompatTextView != null) {
                val params = appCompatTextView.layoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                appCompatTextView.layoutParams = params
                appCompatTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
        }
    }
}

