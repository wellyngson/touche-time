package br.touchetime.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fight(
    var category: String?,
    var style: String?,
    var weight: Int?,
    var state: UiStateFight
) : Parcelable

enum class UiStateFight {
    Default,
    Changed
}
