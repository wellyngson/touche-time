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
    val years: Years,
    val weight: Weight,
    val style: Style,
    val score: Int,
    val foul: Int,
) : Parcelable

enum class Years(val years: String) {
    // Infantil, escolar, cadete...
}

enum class Weight(val weight: Int) {
    // 66,55...
}

enum class Style(val style: String) {
    GrecoRoman("grecoRoman"),
    WomanWrestling("womanWrestling"),
    FreeStyle("freeStyle")
}
