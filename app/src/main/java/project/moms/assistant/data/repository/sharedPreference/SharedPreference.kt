package project.moms.assistant.data.repository.sharedPreference

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(private val context: Context) {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun saveAsleepTime(time: String) {
        with (sharedPreferences.edit()) {
            putString(ASLEEP, time)
            apply()
        }
    }

    fun getAsleepTime(context: Context) : String? {
        return sharedPreferences.getString(ASLEEP, null)
    }

    fun saveAwokeTime(time: String) {
        with (sharedPreferences.edit()) {
            putString(AWOKE, time)
            apply()
        }
    }
    fun getAwokeTime(context: Context) : String? {
        return sharedPreferences.getString(AWOKE, null)
    }

    fun saveDifferenceTime(time: String) {
        with (sharedPreferences.edit()) {
            putString(DIFFERENCE_TIME, time)
            apply()
        }
    }
    fun getDifferenceTime(context: Context) : String? {
        return sharedPreferences.getString(DIFFERENCE_TIME, null)
    }

    companion object {
        private const val PREFERENCE_NAME = "preference_name"
        private const val ASLEEP = "asleep"
        private const val AWOKE = "awoke"
        private const val DIFFERENCE_TIME = "difference_time"
    }
}