package project.moms.assistant.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import project.moms.assistant.data.repository.sharedPreference.SharedPreferences
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Locale

class SleepViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    private val _awakeTime = MutableStateFlow("")
    val awakeTime: StateFlow<String> = _awakeTime.asStateFlow()

    init {
        viewModelScope.launch {
            generateAwakeTimeFlow().collect {
                _awakeTime.value = it
            }
        }
    }

    private fun generateAwakeTimeFlow() : Flow<String> = flow {
        while (true) {
            val wakeupTimeStr = sharedPreferences.getAwokeTime()
            if (wakeupTimeStr != null) {
                val timeWithDate = "${LocalDate.now()}T$wakeupTimeStr"
                val awokeTime = LocalDateTime.parse(timeWithDate)
                val currentTime = LocalDateTime.now()

                val durationDifferenceTime = Duration.between(awokeTime, currentTime)
                val hours = durationDifferenceTime.toHours()
                val minutes = durationDifferenceTime.toMinutes() - (hours * 60)
                val stringDifferenceTime =
                    String.format(Locale.getDefault(), "%02d:%02d", hours, minutes)

                emit(stringDifferenceTime)
            }
            delay(6000)
        }
    }

}