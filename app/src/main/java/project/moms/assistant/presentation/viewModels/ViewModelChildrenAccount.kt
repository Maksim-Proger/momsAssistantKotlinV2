package project.moms.assistant.presentation.viewModels

import androidx.lifecycle.ViewModel
import project.moms.assistant.data.repository.sharedPreference.SharedPreferences
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class ViewModelChildrenAccount(private val sharedPreferences: SharedPreferences) : ViewModel() {

    // TODO доработать типы передаваемых аргументов и блок if
    fun saveDataViewModel(height: String, weight: String, name: String, date: String) {
        if (height.isNotEmpty() && weight.isNotEmpty() && date.isNotEmpty() && name.isNotEmpty()) {
            sharedPreferences.saveHeight(height)
            sharedPreferences.saveWeight(weight)
            sharedPreferences.saveName(name)
            sharedPreferences.saveDate(date)
        }
    }

    private fun getData() {

    }

    fun ageCalculationMethod() : Long { // 2023-01-25
        val birthDate = LocalDate.parse(sharedPreferences.getDate())
        val currentDate = LocalDate.now()
        return ChronoUnit.WEEKS.between(birthDate, currentDate)
    }

}