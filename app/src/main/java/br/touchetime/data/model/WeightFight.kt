package br.touchetime.data.model

import android.content.Context
import br.touchetime.R

object WeightFight {

    private val grecoRomanJrSenior = listOf(
        55, 60, 63, 67, 72, 77, 82, 87, 92, 97, 130
    )

    private val womanWrestlingJrSenior = listOf(
        50, 53, 55, 57, 59, 62, 65, 68, 72, 76
    )

    private val freeStyleJrSenior = listOf(
        57, 61, 65, 70, 74, 79, 86, 92, 97, 125
    )

    // TODO: Adjust this return to delete else branch
    fun getListWeight(fight: Fight?, context: Context): List<Int> =
        when (fight?.style) {
            context.getString(R.string.greco_roman) -> {
                grecoRomanJrSenior
            }
            context.getString(R.string.free_style) -> {
                freeStyleJrSenior
            }
            context.getString(R.string.woman_wrestling) -> {
                womanWrestlingJrSenior
            }
            else -> {
                emptyList()
            }
        }

    fun getListWeightSelected(style: Int, category: Int): List<Int> =
        when (style) {
            R.string.greco_roman -> {
                when (category) {
                    R.string.category_u20, R.string.category_senior -> {
                        grecoRomanJrSenior
                    }
                    else -> {
                        emptyList()
                    }
                }
            }
            R.string.woman_wrestling -> {
                when (category) {
                    R.string.category_u20, R.string.category_senior -> {
                        womanWrestlingJrSenior
                    }
                    else -> {
                        emptyList()
                    }
                }
            }
            else -> {
                when (category) {
                    R.string.category_u20, R.string.category_senior -> {
                        freeStyleJrSenior
                    }
                    else -> {
                        emptyList()
                    }
                }
            }
        }
}
