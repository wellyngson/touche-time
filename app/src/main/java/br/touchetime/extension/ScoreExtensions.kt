package br.touchetime.extension

fun checkVictory(scoreAthleteOne: Int, scoreAthleteTwo: Int, technicalSuperiority: Int): Boolean {
    return (scoreAthleteOne - scoreAthleteTwo) >= technicalSuperiority
}
