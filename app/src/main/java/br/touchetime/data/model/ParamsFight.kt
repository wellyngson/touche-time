package br.touchetime.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParamsFight(
    val technicalSuperiority: Int,
    val numberRounds: Int,
    val timeRounds: String,
    val timeInterval: String,
) : Parcelable