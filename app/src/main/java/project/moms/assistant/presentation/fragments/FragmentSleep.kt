package project.moms.assistant.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import project.moms.assistant.databinding.FragmentSleepBinding
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

        listenerButtons()
    }

    private fun listenerButtons() {
        binding.fellAsleepButton.setOnClickListener {
            binding.fellAsleepMaterialButton.text = currentTime()
        }

        binding.wokeUpButton.setOnClickListener {
            binding.wokeUpMaterialButton.text = currentTime()
        }
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

    private fun timeDifference(time1: String, time2: String) : String {
        val localTime1: LocalTime = LocalTime.parse(time1)
        val localTime2: LocalTime = LocalTime.parse(time2)
        val differenceTime = ChronoUnit.MINUTES.between(localTime1, localTime2)
        return differenceTime.toString()
    }

    private fun checkState() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}