package ru.skillbranch.devintensive.utils

import java.lang.StringBuilder

object Utils {
    fun parseFullName(fullName:String?): Pair<String?, String?> {
        val parts : List<String>? = fullName?.split(" ")
        val firstName = if (parts?.getOrNull(0).isNullOrEmpty()) null else parts?.getOrNull(0)
        val lastName = if (parts?.getOrNull(1).isNullOrEmpty()) null else parts?.getOrNull(1)
//        return Pair(firstName, lastName)
        return firstName to lastName
    }
}
fun Utils.transliteration(payload: String, divider: String = " "): String {
    val dict = hashMapOf<String, String>(
        "а" to "a",
        "б" to "b",
        "в" to "v",
        "г" to "g",
        "д" to "d",
        "е" to "e",
        "ё" to "e",
        "ж" to "zh",
        "з" to "z",
        "и" to "i",
        "й" to "i",
        "к" to "k",
        "л" to "l",
        "м" to "m",
        "н" to "n",
        "о" to "o",
        "п" to "p",
        "р" to "r",
        "с" to "s",
        "т" to "t",
        "у" to "u",
        "ф" to "f",
        "х" to "h",
        "ц" to "c",
        "ч" to "ch",
        "ш" to "sh",
        "щ" to "sh'",
        "ъ" to "",
        "ы" to "i",
        "ь" to "",
        "э" to "e",
        "ю" to "yu",
        "я" to "ya",
        "А" to "A",
        "Б" to "B",
        "В" to "V",
        "Г" to "G",
        "Д" to "D",
        "Е" to "E",
        "Ё" to "E",
        "Ж" to "Zh",
        "З" to "Z",
        "И" to "I",
        "Й" to "I",
        "К" to "K",
        "Л" to "L",
        "М" to "M",
        "Н" to "N",
        "О" to "O",
        "П" to "P",
        "Р" to "R",
        "С" to "S",
        "Т" to "T",
        "У" to "U",
        "Ф" to "F",
        "Х" to "H",
        "Ц" to "C",
        "Ч" to "Ch",
        "Ш" to "Sh",
        "Щ" to "Sh'",
        "Ъ" to "",
        "Ы" to "I",
        "Ь" to "",
        "Э" to "E",
        "Ю" to "Yu",
        "Я" to "Ya"
    )

    val parts:List<String>? = payload.split(" ")
    val newParts:MutableList<String> = mutableListOf()
    parts?.forEach {
        val word = StringBuilder()
        it.forEach {
            word.append(dict.getOrElse(it.toString()) {it.toString()})
        }
        newParts.add(word.toString())
    }
    return newParts.joinToString(divider)
}

fun Utils.toInitials(firstName: String?, lastName: String?): String? {

    if (firstName.isNullOrBlank() && lastName.isNullOrBlank()) {
        return null
    } else {
        return "${if(firstName.isNullOrBlank()) "" else firstName[0].toUpperCase()}" +
                "${if(lastName.isNullOrBlank()) "" else lastName[0].toUpperCase()}"

    }


}


