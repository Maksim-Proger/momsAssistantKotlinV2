package project.moms.assistant.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import project.moms.assistant.data.repository.room.StatisticsDao
import project.moms.assistant.data.models.DiaryRecordingEntity
import project.moms.assistant.data.models.SleepRecordingEntity

class DatabaseViewModel(
    private val statisticsDao: StatisticsDao
) : ViewModel() {

    val allEntries = this.statisticsDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun onSaveEntry(newEntry: String, date: String) {
        viewModelScope.launch {
            val sleepRecordingEntity = SleepRecordingEntity(sleepRecordingId = newEntry, date = date)
            statisticsDao.addSleepRecording(sleepRecordingEntity)
        }
    }


    val allDiaryEntries = this.statisticsDao.getAllDiaryEntries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun onSaveDiaryEntry(newEntry: String) {
        viewModelScope.launch {
            val diaryRecordingEntity = DiaryRecordingEntity(diaryRecording = newEntry)
            statisticsDao.addDiaryRecording(diaryRecordingEntity)
        }
    }

}