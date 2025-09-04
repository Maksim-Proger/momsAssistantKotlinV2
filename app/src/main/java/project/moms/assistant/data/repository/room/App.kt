package project.moms.assistant.data.repository.room

import android.app.Application
import androidx.room.Room

class App : Application() {
    lateinit var db: StatisticsDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            StatisticsDatabase::class.java,
            "db"
        ).build()
    }
}