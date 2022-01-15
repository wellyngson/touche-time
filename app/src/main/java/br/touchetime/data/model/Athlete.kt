package br.touchetime.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Athlete(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val years: Int,
    val weight: Int,
    val style: String,
    val score: Int,
    val foul: Int
) : Parcelable
