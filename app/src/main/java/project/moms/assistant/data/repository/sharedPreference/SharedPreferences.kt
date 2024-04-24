package project.moms.assistant.data.repository.sharedPreference

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(private val context: Context) {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun saveAsleepTime(time: String) {
        with (sharedPreferences.edit()) {
            putString(ASLEEP, time)
            apply()
        }
    }
    fun getAsleepTime() : String? {
        return sharedPreferences.getString(ASLEEP, null)
    }
    fun removeAsleepTime() {
        with(sharedPreferences.edit()) {
            remove(ASLEEP)
            apply()
        }
    }

    fun saveAwokeTime(time: String) {
        with (sharedPreferences.edit()) {
            putString(AWOKE, time)
            apply()
        }
    }
    fun getAwokeTime() : String? {
        return sharedPreferences.getString(AWOKE, null)
    }
    fun removeAwokeTime() {
        with(sharedPreferences.edit()) {
            remove(AWOKE)
            apply()
        }
    }

    fun saveDifferenceTime(time: String) {
        with (sharedPreferences.edit()) {
            putString(DIFFERENCE_TIME, time)
            apply()
        }
    }
    fun getDifferenceTime() : String? {
        return sharedPreferences.getString(DIFFERENCE_TIME, null)
    }
    fun removeDifferenceTime() {
        with(sharedPreferences.edit()) {
            remove(DIFFERENCE_TIME)
            apply()
        }
    }

    fun saveHeight(height: String) {
        with(sharedPreferences.edit()) {
            putString(HEIGHT, height)
            apply()
        }
    }
    fun getHeight() : String? {
        return sharedPreferences.getString(HEIGHT, null)
    }
    fun saveWeight(weight: String) {
        with(sharedPreferences.edit()) {
            putString(WEIGHT, weight)
            apply()
        }
    }
    fun getWeight() : String? {
        return sharedPreferences.getString(WEIGHT, null)
    }
    fun saveName(name: String) {
        with(sharedPreferences.edit()) {
            putString(NAME, name)
            apply()
        }
    }
    fun getName() : String? {
        return sharedPreferences.getString(NAME, null)
    }

    fun saveDate(date: String) {
        with(sharedPreferences.edit()) {
            putString(DATE, date)
            apply()
        }
    }
    fun getDate() : String? {
        return sharedPreferences.getString(DATE, null)
    }

    companion object {
        private const val PREFERENCE_NAME = "preference_name"
        private const val ASLEEP = "asleep"
        const val AWOKE = "wake_up_time"
        private const val DIFFERENCE_TIME = "difference_time"
        private const val HEIGHT = "HEIGHT"
        private const val WEIGHT = "WEIGHT"
        private const val NAME = "NAME"
        private const val DATE = "DATE"
    }
}