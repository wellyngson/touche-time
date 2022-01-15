package br.touchetime.data.model

import br.touchetime.R

object CategoryFight {
    private val categoryList = listOf(
        R.string.category_childish,
        R.string.category_u13,
        R.string.category_u15,
        R.string.category_u18,
        R.string.category_u21,
        R.string.category_senior
    )

    fun getListCategory(): List<Int> = categoryList
}
