package br.touchetime.ui.createfight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.touchetime.data.model.Fight
import br.touchetime.data.model.UiStateFight
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateFightViewModel : ViewModel() {

    private var fight: Fight? = Fight(
        "",
        "",
        0,
        UiStateFight.Default
    )

    private val _fight = MutableStateFlow(
        Fight(
            "",
            "",
            0,
            UiStateFight.Default
        )
    )
    val fightStateFlow: StateFlow<Fight> get() = _fight

    fun getFight(): Fight? = fight

    fun setCategory(category: String) {
        fight?.category = category

        viewModelScope.launch {
            _fight.value = _fight.value.copy(
                category = category,
                state = UiStateFight.Changed
            )
        }
    }

    fun setStyle(style: String) {
        fight?.style = style

        viewModelScope.launch {
            _fight.value = _fight.value.copy(
                style = style,
                state = UiStateFight.Changed
            )
        }
    }

    fun setWeight(weight: Int) {
        fight?.weight = weight

        viewModelScope.launch {
            _fight.value = _fight.value.copy(
                weight = weight,
                state = UiStateFight.Changed
            )
        }
    }
}
