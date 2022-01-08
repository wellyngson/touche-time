package br.touchetime.extension

fun changeTimer(minutes: Int, seconds: Int, value: Int): String {
    return if (minutes == 0 && seconds == 10 && value == -10) {
        stringFormat(seconds, minutes)
    } else {
        var secondsFinal = seconds
        secondsFinal += value

        when {
            secondsFinal > 50 -> {
                stringFormat(0, minutes + 1)
            }
            secondsFinal < 0 -> {
                stringFormat(50, minutes - 1)
            }
            else -> {
                stringFormat(secondsFinal, minutes)
            }
        }
    }
}

fun stringFormat(seconds: Int, minutes: Int): String {
    val secondsFinal = String.format("%02d", seconds)
    val minutesFinal = String.format("%02d", minutes)

    return "$minutesFinal:$secondsFinal"
}
