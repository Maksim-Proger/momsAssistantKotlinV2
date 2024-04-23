package project.moms.assistant.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import project.moms.assistant.data.repository.models.SleepRecording
import kotlinx.coroutines.flow.Flow
import project.moms.assistant.data.repository.models.DiaryRecording

@Dao
interface StatisticsDao {
    @Query("SELECT * FROM sleep_recording")
    fun getAll(): Flow<List<SleepRecording>>

    @Insert
    suspend fun addSleepRecording(sleepRecording: SleepRecording)

    @Query("SELECT * FROM diary_recording")
    fun getAllDiaryEntries(): Flow<List<DiaryRecording>>

    @Insert
    suspend fun addDiaryRecording(diaryRecording: DiaryRecording)
}