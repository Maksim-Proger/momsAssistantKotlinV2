package project.moms.assistant.presentation

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class LogicAssistant {
    fun assistantMethod(context: Context, question: String) : String {
        return "Привет"
    }

    fun read(context: Context, fileName: String?): String {
        val stringBuilder = StringBuilder()
        try {
            val inputStream = context.assets.open(fileName!!)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
                stringBuilder.append("\n")
            }
            bufferedReader.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}