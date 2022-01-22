package br.touchetime.data.model

import br.touchetime.R

object CategoryFight {
    fun getListCategory(): List<Int> {
        return listOf(
            R.string.category_childish,
            R.string.category_u13,
            R.string.category_u15,
            R.string.category_u17,
            R.string.category_u20,
            R.string.category_senior
        )
    }
}
