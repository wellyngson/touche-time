package br.touchetime.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fight(
    var category: Category?,
    var style: Style?,
    var weight: Weight?
) : Parcelable

@Parcelize
data class Category(
    var category: String?
) : Parcelable

@Parcelize
data class Style(
    var style: String?
) : Parcelable

@Parcelize
data class Weight(
    var weight: Int?
) : Parcelable