package project.moms.assistant.presentation

import android.view.View
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class DatePickerDialog(private val fragmentManager: FragmentManager) {

    private val calendar: Calendar = Calendar.getInstance()

    fun addNewTime(view: View, onTimeSelected: (String) -> Unit) {
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
}