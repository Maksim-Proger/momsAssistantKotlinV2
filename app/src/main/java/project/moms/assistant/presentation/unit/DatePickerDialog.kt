package project.moms.assistant.presentation.unit

import android.view.View
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class DatePickerDialog(private val fragmentManager: FragmentManager) {

    private val calendar: Calendar = Calendar.getInstance()

    fun changeTime(view: View, onTimeSelected: (String) -> Unit) {
        view.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Укажите время")
                .setHour(calendar.get(Calendar.HOUR))
                .setMinute(calendar.get(Calendar.MINUTE))
                .build().apply {
                    addOnPositiveButtonClickListener {
                        calendar.set(Calendar.HOUR, this.hour)
                        calendar.set(Calendar.MINUTE, this.minute)
                        val formattedTime = String.format("%02d:%02d", this.hour, this.minute)
                        onTimeSelected(formattedTime)
                    }
                }
            picker.show(fragmentManager, "TimePicker")
        }
    }

    fun addNewTimes(view: View, onTimesSelected: (String, String) -> Unit) {
        view.setOnClickListener {
            val pickerFirst = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Укажите начальное время")
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
                .build()

            pickerFirst.addOnPositiveButtonClickListener {
                val firstHour = pickerFirst.hour
                val firstMinute = pickerFirst.minute
                val formattedTimeFirst = String.format("%02d:%02d", firstHour, firstMinute)

                val pickerSecond = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setTitleText("Укажите конечное время")
                    .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                    .setMinute(calendar.get(Calendar.MINUTE))
                    .build()

                pickerSecond.addOnPositiveButtonClickListener {
                    val secondHour = pickerSecond.hour
                    val secondMinute = pickerSecond.minute
                    val formattedTimeSecond = String.format("%02d:%02d", secondHour, secondMinute)
                    onTimesSelected(formattedTimeFirst, formattedTimeSecond)
                }

                pickerSecond.show(fragmentManager, "EndTimePicker")
            }

            pickerFirst.show(fragmentManager, "StartTimePicker")
        }
    }
}