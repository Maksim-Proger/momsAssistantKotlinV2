package project.moms.assistant.data.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import project.moms.assistant.data.models.DiaryRecordingEntity
import project.moms.assistant.data.models.SleepRecordingEntity

@Database(
    entities = [
        SleepRecordingEntity::class,
        DiaryRecordingEntity::class
    ],
    version = 1
)
abstract class StatisticsDatabase : RoomDatabase() {
    abstract fun statisticsDao(): StatisticsDao
}