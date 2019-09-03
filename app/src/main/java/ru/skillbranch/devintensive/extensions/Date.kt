package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}


fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
        else -> throw IllegalStateException("invalid unit")
    }
    this.time = time
    return this
}


fun Date.humanizeDiff(): String {
    val date = this
    val diff = (Date().time - date.time) / 1000
    val days = diff / 60 / 60 / 24
    return when (diff) {
        in 0L until 1L -> "только что"
        in 1L until 45L -> "несколько секунд назад"
        in 45L until 75L -> "минуту назад"
        in 75L until 45 * 60L -> "${diff / 60} " +
                when (diff / 60) {
                    1L, 21L, 31L, 41L -> "минуту"
                    in 2L until 5L, in 22L until 25L, in 32L until 35L, in 42L until 45L -> "минуты"
                    else -> "минут"
                } + " назад"
        in 45 * 60L until 75 * 60L -> "час назад"
        in 75 * 60L until 22 * 60 * 60L -> "${diff / 60 / 60} " +
                when (diff / 60 / 60) {
                    1L, 21L, 31L, 41L -> "час"
                    in 2L until 5L, in 22L until 25L, in 32L until 35L, in 42L until 45L -> "часа"
                    else -> "часов"
                } + " назад"
        in 22 * 60 * 60L until 26 * 60 * 60L -> "день назад"
        in 26 * 60 * 60L until 360 * 24 * 60 * 60L -> "${days} " +
                when {
                    days % 100 / 10 == 1L -> "дней"
                    days % 10 == 1L -> "день"
                    days % 10 in 2L..4L -> "дня"
                    else -> "дней"
                } + " назад"
        in 360 * 24 * 60 * 60L..Long.MAX_VALUE -> "более года назад"
        in -1L until 0L -> "только что"
        in -45L until -1L -> "через несколько секунд"
        in -75L until -45L -> " через минуту"
        in -45 * 60L until -75L -> "через ${-diff / 60} " +
                when (diff / 60) {
                    -1L, -21L, -31L, -41L -> "минуту"
                    in -4L..-2L, in -24L..-22L, in -34L..-32L, in -44L..-42L -> "минуты"
                    else -> "минут"
                }
        in -75 * 60L until -45 * 60L -> "через час"
        in -22 * 60 * 60L until -75 * 60L -> "через ${-diff / 60 / 60} " +
                when (diff / 60 / 60) {
                    -1L, -21L, -31L, -41L -> "час"
                    in -4L..-2L, in -24L..-22L, in -34L..-32L, in -44L..-42L -> "часа"
                    else -> "часов"
                }
        in -26 * 60 * 60L until -22 * 60 * 60L -> "через день"
        in -360 * 24 * 60 * 60L until -26 * 60 * 60L -> "через ${-days} " +
                when {
                    days % 100 / 10 == -1L -> "дней"
                    days % 10 == -1L -> "день"
                    days % 10 in -4L..-2L -> "дня"
                    else -> "дней"
                }
        else -> "более чем через год"

    }
}


enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(units: Int): String = when {
        units % 100 / 10 == 1 || units % 100 / 10 == -1 -> "$units " +
                when (this) {
                    SECOND -> "секунд"
                    MINUTE -> "минут"
                    HOUR -> "часов"
                    DAY -> "дней"
                }
        units % 10 == 1 || units % 10 == -1 -> "$units " +
                when (this) {
                    SECOND -> "секунду"
                    MINUTE -> "минуту"
                    HOUR -> "час"
                    DAY -> "день"
                }
        units % 10 in 2..4 || units % 10 in -4..-2 -> "$units " +
                when (this) {
                    SECOND -> "секунды"
                    MINUTE -> "минуты"
                    HOUR -> "часа"
                    DAY -> "дня"
                }
        else -> "$units " +
                when (this) {
                    SECOND -> "секунд"
                    MINUTE -> "минут"
                    HOUR -> "часов"
                    DAY -> "дней"
                }
    }
}


fun Date.shortFormat(): String? {
    val pattern = if(this.isSameDay(Date())) "HH:mm" else "dd.MM.yy"
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.isSameDay(date: Date): Boolean {
    val day1 = this.time/ DAY
    val day2 = date.time/ DAY
    return day1 == day2

}