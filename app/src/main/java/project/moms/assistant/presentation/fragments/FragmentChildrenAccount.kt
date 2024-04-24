package project.moms.assistant.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import project.moms.assistant.R
import project.moms.assistant.data.repository.sharedPreference.SharedPreferences
import project.moms.assistant.databinding.FragmentChildrenAccountBinding
import project.moms.assistant.presentation.viewModels.ViewModelChildrenAccount


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
        viewModel = ViewModelChildrenAccount(sharedPreferences)

        listenerButtons()
        getData()
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
        val date = "2023-01-25"
        viewModel.saveDataViewModel(height, weight, name, date)
    }

    private fun getData() {
        if (
            sharedPreferences.getHeight() != null &&
            sharedPreferences.getWeight() != null &&
                sharedPreferences.getName() != null) {
            binding.editHeight.setText(sharedPreferences.getHeight())
            binding.editWeight.setText(sharedPreferences.getWeight())
            binding.editName.setText(sharedPreferences.getName())
            // Тестовый вариант
            binding.editAge.setText(viewModel.ageCalculationMethod().toString() + " "
                    + resources.getString(R.string.weeks_old))
        }
    }

    private fun dateSelection() {

    }

    private fun checkStateButtonSave() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}














