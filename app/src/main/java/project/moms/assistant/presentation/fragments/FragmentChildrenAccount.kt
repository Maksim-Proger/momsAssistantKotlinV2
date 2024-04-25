package project.moms.assistant.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import project.moms.assistant.R
import project.moms.assistant.data.repository.sharedPreference.SharedPreferences
import project.moms.assistant.databinding.FragmentChildrenAccountBinding
import project.moms.assistant.presentation.viewModels.ViewModelChildrenAccount
import java.text.SimpleDateFormat
import java.util.Calendar

class FragmentChildrenAccount : Fragment() {
    private var _binding : FragmentChildrenAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: ViewModelChildrenAccount

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChildrenAccountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = SharedPreferences(requireContext())
        viewModel =
            ViewModelProvider(
                this, ViewModelChildrenAccount
                    .Factory(sharedPreferences)
            )[ViewModelChildrenAccount::class.java]

        listenerButtons()
        getData()
        dateSelection()

        viewModel.ageCalculationMethod()
        lifecycleScope.launchWhenStarted {
            viewModel.weeks.collect{weeks ->
                binding.editAge.setText(getString(R.string.weeks_old, weeks))
            }
        }
    }

    private fun listenerButtons() {
        binding.buttonSave.setOnClickListener {
            saveData()
        }
    }

    // TODO добавить проверку
    private fun saveData() {
        val height = binding.editHeight.text.toString()
        val weight = binding.editWeight.text.toString()
        val name = binding.editName.text.toString()
        val date = binding.editDate.text.toString()
        viewModel.saveDataViewModel(height, weight, name, date)
    }

    private fun getData() {
        if (
            sharedPreferences.getHeight() != null &&
            sharedPreferences.getWeight() != null &&
            sharedPreferences.getName() != null &&
            sharedPreferences.getDate() != null) {

            binding.editHeight.setText(sharedPreferences.getHeight())
            binding.editWeight.setText(sharedPreferences.getWeight())
            binding.editName.setText(sharedPreferences.getName())
            binding.editDate.setText(sharedPreferences.getDate())
            // Тестовый вариант
//            binding.editAge.setText(viewModel.ageCalculationMethod().toString() + " "
//                    + resources.getString(R.string.weeks_old))
        }
    }

    private fun dateSelection() : String {
        val calendar : Calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        binding.selectDate.setOnClickListener {
            val constraints = CalendarConstraints.Builder()
                .setOpenAt(calendar.timeInMillis)
                .build()
            val dateDialog = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraints)
                .setTitleText("Выберите дату")
                .build()
            dateDialog.addOnPositiveButtonClickListener { timeInMillis ->
                calendar.timeInMillis = timeInMillis
                val selectedDate = dateFormat.format(calendar.time)
                binding.editDate.setText(selectedDate)
            }
            dateDialog.show(requireActivity().supportFragmentManager, "DatePicker")
        }
        return ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}














