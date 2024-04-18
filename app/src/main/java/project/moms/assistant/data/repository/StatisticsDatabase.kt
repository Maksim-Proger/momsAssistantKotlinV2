package project.moms.assistant.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import project.moms.assistant.data.repository.models.SleepRecording

@Database(
    entities = [SleepRecording::class],
    version = 1
)
abstract class StatisticsDatabase : RoomDatabase() {
    abstract fun StatisticsDao() : StatisticsDao
}