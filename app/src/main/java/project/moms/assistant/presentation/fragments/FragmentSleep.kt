package project.moms.assistant.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.moms.assistant.data.repository.room.App
import project.moms.assistant.data.repository.sharedPreference.SharedPreferences
import project.moms.assistant.databinding.FragmentSleepBinding
import project.moms.assistant.presentation.unit.DatePickerDialog
import project.moms.assistant.presentation.unit.OnScrollChangeListener
import project.moms.assistant.presentation.viewModels.DatabaseViewModel
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale

class FragmentSleep : Fragment() {
    private var _binding : FragmentSleepBinding? = null
    private val binding : FragmentSleepBinding
        get() {
            return _binding!!
        }

    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var sharedPreferences: SharedPreferences

    private val viewModel: DatabaseViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val statisticsDao = (requireContext().applicationContext as App).db.statisticsDao()
                return DatabaseViewModel(statisticsDao) as T
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSleepBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scrollViewContent.viewTreeObserver.addOnScrollChangedListener {
            if (_binding != null) { // делаем проверку, чтобы в моменты вызова onScrollChanged() _binding не был равен null
                val maxScroll = binding.scrollViewContent.getChildAt(0).height -
                        binding.scrollViewContent.height
                val currentScroll = binding.scrollViewContent.scrollY
                val percentageScrolled = currentScroll.toFloat() / maxScroll.toFloat()

                // Вызов метода onScrollChanged активности
                (activity as? OnScrollChangeListener)?.onScrollChanged(percentageScrolled) // TODO детально разобраться эту строку
            }
        }

        sharedPreferences = SharedPreferences(requireContext())
        datePickerDialog = DatePickerDialog(requireFragmentManager()) // TODO попробовать избавиться от устаревшего метода
        listenerButtons()
    }

    private fun listenerButtons() {
        // Заснул
        binding.fellAsleepButton.setOnClickListener {
            sharedPreferences.saveAsleepTime(currentTime())
            binding.fellAsleepMaterialButton.text = sharedPreferences.getAsleepTime()

            // очищаем поля проснулся и разница
            sharedPreferences.removeAwokeTime()
            sharedPreferences.removeDifferenceTime()
        }

        // Проснулся
        binding.wokeUpButton.setOnClickListener {
            sharedPreferences.saveAwokeTime(currentTime())
            binding.wokeUpMaterialButton.text = sharedPreferences.getAwokeTime()

            // временная реализация
            timeDifference()

            // Записываем в базу
            addResultTimeToDatabase(resultTime())
        }

        // Меняем время заснул
        binding.fellAsleepMaterialButton.setOnClickListener {
            datePickerDialog.changeTime(binding.fellAsleepMaterialButton) { selectedTime ->
                sharedPreferences.saveAsleepTime(selectedTime)
                binding.fellAsleepMaterialButton.text = sharedPreferences.getAsleepTime()
            }

            // очищаем поля проснулся и разница
            sharedPreferences.removeAwokeTime()
            sharedPreferences.removeDifferenceTime()
        }

        // Меняем время проснулся
        binding.wokeUpMaterialButton.setOnClickListener {
            datePickerDialog.changeTime(binding.wokeUpMaterialButton) { selectedTime ->
                sharedPreferences.saveAwokeTime(selectedTime)
                binding.wokeUpMaterialButton.text = sharedPreferences.getAwokeTime()

                // временная реализация
                timeDifference()

                // Записываем в базу
                addResultTimeToDatabase(resultTime())
            }
        }

        // Добавляем сон вручную
        setupTimeSelection()

        // Привязываем bottomSheet к обработчику кнопки
        binding.settingsButton.setOnClickListener {
            val bottomSheet: SleepConfiguratorFragment =  SleepConfiguratorFragment()
            bottomSheet.show(requireFragmentManager(), bottomSheet.tag)
        }
    }

    private fun currentTime() : String {
        val localTime: LocalTime = LocalTime.now()
        val timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return localTime.format(timeFormat)
    }

    private fun currentDate() : String {
        val calendar: Calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy",
            Locale.getDefault())
        return dateFormat.format(calendar.time).toString()
    }

    private fun resultTime() : String{
        val asleepTime = sharedPreferences.getAsleepTime()
        val awokeTime = sharedPreferences.getAwokeTime()
        val differenceTime = sharedPreferences.getDifferenceTime()
        return "$asleepTime : $awokeTime : $differenceTime"
    }

    private fun addResultTimeToDatabase(resultTime: String) {
        viewModel.onSaveEntry(resultTime, currentDate())
    }

    private fun timeDifference() {
        val asleepTime: LocalTime = LocalTime.parse(sharedPreferences.getAsleepTime())
        val awokeTime: LocalTime = LocalTime.parse(sharedPreferences.getAwokeTime())

        if (sharedPreferences.getAsleepTime() != null && sharedPreferences.getAwokeTime() != null) {
            var differenceTime = ChronoUnit.MINUTES.between(asleepTime, awokeTime)

            if (differenceTime < 0) {
                differenceTime += 24 * 60
            }

            // Переводим минуты в часы
            val minutes = differenceTime.toInt()
            val hours = minutes / 60
            val remainingMinutes = minutes % 60
            val newDifferenceTime = "${hours}ч ${remainingMinutes}мин "
            sharedPreferences.saveDifferenceTime(newDifferenceTime)
            binding.textViewTimeDifference.text = newDifferenceTime
        }
    }

    private fun setupTimeSelection() {
        datePickerDialog.addNewTimes(binding.addDreamButton) { startTime, endTime ->
            sharedPreferences.saveAsleepTime(startTime)
            sharedPreferences.saveAwokeTime(endTime)

            // временная реализация
            timeDifference()

            // Записываем в базу
            addResultTimeToDatabase(resultTime())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}