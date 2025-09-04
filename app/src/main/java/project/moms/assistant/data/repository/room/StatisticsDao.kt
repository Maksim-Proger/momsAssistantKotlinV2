package project.moms.assistant.data.repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import project.moms.assistant.data.models.DiaryRecordingEntity
import project.moms.assistant.data.models.SleepRecordingEntity

@Dao
interface StatisticsDao {
    @Query("SELECT * FROM sleep_recording ORDER BY date DESC")
    fun getAll(): Flow<List<SleepRecordingEntity>>

    @Insert
    suspend fun addSleepRecording(sleepRecordingEntity: SleepRecordingEntity)

    @Query("SELECT * FROM diary_recording")
    fun getAllDiaryEntries(): Flow<List<DiaryRecordingEntity>>

    @Insert
    suspend fun addDiaryRecording(diaryRecordingEntity: DiaryRecordingEntity)
}