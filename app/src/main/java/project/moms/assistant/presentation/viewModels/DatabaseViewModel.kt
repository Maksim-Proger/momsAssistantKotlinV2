package project.moms.assistant.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import project.moms.assistant.data.repository.StatisticsDao
import project.moms.assistant.data.repository.models.SleepRecording

class DatabaseViewModel(
    private val statisticsDao: StatisticsDao
) : ViewModel() {

    val allEntries = this.statisticsDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L), // TODO разобраться что это за число
            initialValue = emptyList()
        )

    fun onSaveEntry(newEntry: String) {
        viewModelScope.launch {
            statisticsDao.addSleepRecording(SleepRecording(newEntry))
        }
    }


}