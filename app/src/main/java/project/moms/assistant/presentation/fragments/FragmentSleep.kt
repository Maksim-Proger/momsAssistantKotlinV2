package project.moms.assistant.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import project.moms.assistant.data.repository.sharedPreference.SharedPreference
import project.moms.assistant.databinding.FragmentSleepBinding
import project.moms.assistant.presentation.DatePickerDialog
import project.moms.assistant.presentation.OnScrollChangeListener
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
    private lateinit var sharedPreference: SharedPreference

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

        sharedPreference = SharedPreference(requireContext())
        datePickerDialog = DatePickerDialog(requireFragmentManager()) // TODO попробовать избавиться от устаревшего метода
        listenerButtons()
    }

    private fun listenerButtons() {
        // Заснул
        binding.fellAsleepButton.setOnClickListener {
            sharedPreference.saveAsleepTime(currentTime())
            binding.fellAsleepMaterialButton.text = sharedPreference.getAsleepTime()

            // очищаем поля проснулся и разница
            sharedPreference.removeAwokeTime()
            sharedPreference.removeDifferenceTime()
        }

        // Проснулся
        binding.wokeUpButton.setOnClickListener {
            sharedPreference.saveAwokeTime(currentTime())
            binding.wokeUpMaterialButton.text = sharedPreference.getAwokeTime()

            // временная реализация
            timeDifference()
        }

        // Меняем время заснул
        binding.fellAsleepMaterialButton.setOnClickListener {
            datePickerDialog.changeTime(binding.fellAsleepMaterialButton) { selectedTime ->
                sharedPreference.saveAsleepTime(selectedTime)
                binding.fellAsleepMaterialButton.text = sharedPreference.getAsleepTime()
            }
        }

        // Меняем время проснулся
        binding.wokeUpMaterialButton.setOnClickListener {
            datePickerDialog.changeTime(binding.wokeUpMaterialButton) { selectedTime ->
                sharedPreference.saveAwokeTime(selectedTime)
                binding.wokeUpMaterialButton.text = sharedPreference.getAwokeTime()

                // временная реализация
                timeDifference()
            }
        }

        // Добавляем сон вручную
        setupTimeSelection()
    }

    private fun currentTime() : String {
        val localTime: LocalTime = LocalTime.now()
        val timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return localTime.format(timeFormat)
    }

    private fun currentDate() : String {
        val calendar: Calendar = Calendar.getInstance()
        val dateFormat: SimpleDateFormat = SimpleDateFormat("EEEE, dd.MM.yyyy",
            Locale.getDefault())
        return dateFormat.format(calendar.time).toString()
    }

    private fun timeDifference(){
        val asleepTime: LocalTime = LocalTime.parse(sharedPreference.getAsleepTime())
        val awokeTime: LocalTime = LocalTime.parse(sharedPreference.getAwokeTime())
        if (sharedPreference.getAsleepTime() != null && sharedPreference.getAwokeTime() != null) {
            val differenceTime = ChronoUnit.MINUTES.between(asleepTime, awokeTime)

            // Переводим минуты в часы
            val minutes = differenceTime.toInt()
            val hours = minutes / 60
            val remainingMinutes = minutes % 60
            val newDifferenceTime = "${hours}ч ${remainingMinutes}мин "

            binding.textViewTimeDifference.text = newDifferenceTime
        }
    }


    private fun setupTimeSelection() {
        datePickerDialog.addNewTimes(binding.addDreamButton) { startTime, endTime ->
            Toast.makeText(requireContext(),
                "Начальное время: $startTime, Конечное время: $endTime", Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun checkState() {
        // TODO дописать логи скрывания кнопки проснулся
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}