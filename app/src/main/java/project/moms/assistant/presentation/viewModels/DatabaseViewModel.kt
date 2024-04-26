package project.moms.assistant.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import project.moms.assistant.data.repository.StatisticsDao
import project.moms.assistant.data.repository.models.DiaryRecording
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

    // TODO переделать скрин в дипломе
    fun onSaveEntry(newEntry: String, date: String) {
        viewModelScope.launch {
            val sleepRecording = SleepRecording(sleepRecordingId = newEntry, date = date)
            statisticsDao.addSleepRecording(sleepRecording)
        }
    }


    val allDiaryEntries = this.statisticsDao.getAllDiaryEntries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L), // TODO разобраться что это за число
            initialValue = emptyList()
        )

    fun onSaveDiaryEntry(newEntry: String) {
        viewModelScope.launch {
            val diaryRecording = DiaryRecording(diaryRecording = newEntry)
            statisticsDao.addDiaryRecording(diaryRecording)
        }
    }

}