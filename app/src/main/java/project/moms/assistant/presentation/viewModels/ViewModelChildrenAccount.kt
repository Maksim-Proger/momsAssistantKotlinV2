package project.moms.assistant.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import project.moms.assistant.data.repository.sharedPreference.SharedPreferences
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class ViewModelChildrenAccount(private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val _weeks = MutableStateFlow("")
    val weeks = _weeks.asStateFlow()

    fun saveDataViewModel(height: String, weight: String, name: String, date: String) {
        if (height.isNotEmpty() && weight.isNotEmpty() && name.isNotEmpty() && date.isNotEmpty()) {
            sharedPreferences.saveHeight(height)
            sharedPreferences.saveWeight(weight)
            sharedPreferences.saveName(name)
            sharedPreferences.saveDate(date)
        }
    }

    fun ageCalculationMethod() {
        viewModelScope.launch {
            if (sharedPreferences.getDate() != null) {
                val birthDate = LocalDate.parse(sharedPreferences.getDate())
                val currentDate = LocalDate.now()
                _weeks.value = ChronoUnit.WEEKS.between(birthDate, currentDate).toString()
            }
        }
    }

    class Factory(private val sharedPreferences: SharedPreferences) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ViewModelChildrenAccount::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ViewModelChildrenAccount(sharedPreferences) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}