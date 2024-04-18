package project.moms.assistant.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import project.moms.assistant.data.repository.models.SleepRecording
import kotlinx.coroutines.flow.Flow

// TODO сформировать запросы
@Dao
interface StatisticsDao {
    @Query("SELECT * FROM sleep_recording")
    fun getAll(): Flow<List<SleepRecording>>

    @Insert
    suspend fun addSleepRecording(sleepRecording: SleepRecording)
}