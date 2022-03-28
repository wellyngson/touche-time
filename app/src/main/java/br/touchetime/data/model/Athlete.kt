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
    val years: String,
    val style: String,
    val weight: Int,
    val defeat: Int,
    val win: Int,
    val fight: Int,
) : Parcelable
