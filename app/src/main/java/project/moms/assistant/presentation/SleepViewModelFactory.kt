package project.moms.assistant.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.moms.assistant.presentation.viewModels.SleepViewModel
import project.moms.assistant.data.repository.sharedPreference.SharedPreferences

class SleepViewModelFactory(private val sharedPreferences: SharedPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepViewModel::class.java)) {
            return SleepViewModel(sharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
