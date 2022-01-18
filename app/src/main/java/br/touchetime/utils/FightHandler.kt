package br.touchetime.utils

import br.touchetime.data.model.Category
import br.touchetime.data.model.Style
import br.touchetime.data.model.Weight

interface CategoryHandler {
    fun onClick(category: Category)
}

interface StyleHandler {
    fun onClick(style: Style)
}

interface WeightHandler {
    fun onClick(weight: Weight)
}
