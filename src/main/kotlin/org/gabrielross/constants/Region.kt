package org.gabrielross.constants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.IOException

@Serializable
enum class Region {
    @SerialName("kanto")
    Kanto,

    @SerialName("johto")
    Johto,

    @SerialName("hoenn")
    Hoenn,

    @SerialName("sinnoh")
    Sinnoh,

    @SerialName("unova")
    Unova,

    @SerialName("kalos")
    Kalos,

    @SerialName("alola")
    Alola,

    @SerialName("galar")
    Galar,

    @SerialName("hisui")
    Hisui,

    @SerialName("paldea")
    Paldea;

    override fun toString(): String = when(this) {
        Kanto -> "kanto"
        Johto -> "johto"
        Hoenn -> "hoenn"
        Sinnoh -> "sinnoh"
        Unova -> "unova"
        Kalos -> "kalos"
        Alola -> "alola"
        Galar -> "galar"
        Hisui -> "hisui"
        Paldea -> "paldea"
    }

}