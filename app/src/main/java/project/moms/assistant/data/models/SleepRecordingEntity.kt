package project.moms.assistant.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_recording")
data class SleepRecordingEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "sleep_recording_id")
    val sleepRecordingId: String,
    @ColumnInfo(name = "date")
    val date: String
)