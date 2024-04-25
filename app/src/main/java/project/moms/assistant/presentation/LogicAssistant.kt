package project.moms.assistant.presentation

import android.content.Context
import opennlp.tools.stemmer.PorterStemmer
import opennlp.tools.tokenize.SimpleTokenizer
import project.moms.assistant.data.repository.sharedPreference.SharedPreferences
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class LogicAssistant {

    fun assistantMethod(context: Context, question: String) : String {
        val sharedPreferences = SharedPreferences(context)

        val tokenizer: SimpleTokenizer = SimpleTokenizer.INSTANCE
        val tokens: Array<String> = tokenizer.tokenize(question)

        val stemmer = PorterStemmer()
        for (i in tokens.indices) {
            tokens[i] = stemmer.stem(tokens[i])
        }

        if (containsAnyNormalized(tokens, "привет", "здравствуй", "добрый", "день")) {
            return "Привет"
        } else if (containsAnyNormalized(tokens, "имя","зовут","представься")) {
            return "Меня зовут MaksBot"
        } else if (containsAnyNormalized(tokens, "умеешь", "можешь")) {
            return BASIC_ANSWER
        } else if (containsAnyNormalized(tokens, "питание","питания","питании","диета","диеты")) {
            return readFile(context, "powerSupply.txt")
        } else if (containsAnyNormalized(tokens, "алкоголь","алкоголя","спиртное", "спиртных")) {
            return readFile(context, "alcohol.txt")
        } else if (containsAnyNormalized(tokens, "молоко","молоком","количество","увеличить")) {
            return readFile(context, "milkProblem.txt")
        } else if (containsAnyNormalized(tokens, "позы", "кормление","кормить")) {
            return readFile(context, "feedingPoses.txt")
        } else if (containsAnyNormalized(tokens, "режим","режимы")) {
            return readFile(context, "feedingMode.txt")
        } else if (containsAnyNormalized(tokens, "что такое лактостаз", "про лактостаз", "о лактостазе")) {
            return readFile(context, "lactostasis.txt")
        } else if (containsAnyNormalized(tokens, "справиться с лактостазом", "у тебя лактостаз", "при лактостазе")) {
            return readFile(context, "lactostasisTreatment.txt")
        } else if (containsAnyNormalized(tokens, "ребенок", "дети")) {
            return CHILD
        } else if (containsAnyNormalized(tokens, "родители", "родитель")) {
            return PARENTS
        } else if (containsAnyNormalized(tokens, "моего малыша", "зовут моего")) {
            return sharedPreferences.getName().toString()
        }

        return "Пока я не могу ответить вам на этот вопрос. Попробуйте его переформулировать."
    }

    private fun containsAnyNormalized(
        array: Array<String>, vararg normalizedValues: String
    ) : Boolean {
        val combined = array.joinToString(" ").lowercase()
        normalizedValues.forEach { normalizedValue ->
            if (combined.contains(normalizedValue.lowercase())) {
                return true
            }
        }
        return false
    }

    private fun readFile(context: Context, fileName: String?): String {
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

    companion object {
        private const val BASIC_ANSWER = "Я могу дать советы помогающие мамам справиться со " +
                "сложностями в первые годы" +
                "жизни. Например, я могу дать советы по вопросу грудного вскармливания." +
                "Рассказать про режимы и позы кормления или" +
                "рассказать про необходимость диеты для мамы. Могу дать ответы на часто" +
                "возникающие вопросы, например, можно ли маме выпивать алкоголь в небольшом" +
                "количестве, естественно. И это далеко не все мои возможности."
        private const val CHILD = "Человек, который не достиг совершеннолетия, установленного " +
                "законом. А еще, это ваше меленькое чудо!)"
        private const val PARENTS = "Родители являются ближайшими родственниками их детей. " +
                "Роль родителей в отношении ребёнка имеет сложный и " +
                "глубокий характер и колеблется в зависимости от культуры, религии и народа. " +
                "Родители как воспитатели несут ответственность за поведение своего ребёнка " +
                "в обществе. "
    }
}