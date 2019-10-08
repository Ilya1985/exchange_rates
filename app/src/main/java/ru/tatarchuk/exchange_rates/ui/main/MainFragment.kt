package ru.tatarchuk.exchange_rates.ui.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.DatePicker
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import org.threeten.bp.LocalDate
import ru.tatarchuk.exchange_rates.R
import ru.tatarchuk.exchange_rates.ui.main.model.DailyRates
import ru.tatarchuk.exchange_rates.utils.Const.FIRST_DATE_MILLIS
import ru.tatarchuk.exchange_rates.utils.IDateFormatter

class MainFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        private val TAG = "<>${MainFragment::class.java.simpleName}"
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var progress: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private val adapter = MainAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycle_view)
        recyclerView.adapter = adapter
        progress = view.findViewById(R.id.progress)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.progress.observe(viewLifecycleOwner, Observer { setProgressState(it) })
        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.data = it.data
            setDate(it)
        })

    }

    private fun setProgressState(isOn: Boolean) {
        progress.visibility = if (isOn) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dialog_date_picker -> createDatePicker()?.show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createDatePicker(): DatePickerDialog? {

        val dialog = context?.let { context ->
            DatePickerDialog(
                context,
                this,
                viewModel.currentDate().year,
                viewModel.currentDate().monthValue,
                viewModel.currentDate().dayOfMonth
            )
        }

        dialog?.datePicker?.maxDate = viewModel.maxDate.toInstant().toEpochMilli()
        dialog?.datePicker?.minDate = FIRST_DATE_MILLIS
        return dialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.i(TAG, "date = ${LocalDate.of(year, month + 1, dayOfMonth).format(IDateFormatter.requestDateFormat)}")
        viewModel.getByDate(LocalDate.of(year, month + 1, dayOfMonth))
    }

    private fun setDate(data: DailyRates) {
        activity?.actionBar?.title = data.date.format(IDateFormatter.requestDateFormat)
    }
}