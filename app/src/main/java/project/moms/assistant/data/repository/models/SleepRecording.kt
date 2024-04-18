package project.moms.assistant.data.repository.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_recording")
data class SleepRecording(
    @PrimaryKey
    @ColumnInfo(name = "sleep_recording_id")
    val sleepRecordingId: String
)