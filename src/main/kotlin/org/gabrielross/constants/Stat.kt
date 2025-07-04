package org.gabrielross.constants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Stat {
    @SerialName("health")
    Health,

    @SerialName("attack")
    Attack,

    @SerialName("defense")
    Defense,

    @SerialName("special-attack")
    SpecialAttack,

    @SerialName("special-defense")
    SpecialDefense,

    @SerialName("speed")
    Speed;

    override fun toString(): String = when(this) {
        Health -> "hp"
        Attack -> "atk"
        Defense -> "def"
        SpecialAttack -> "spa"
        SpecialDefense -> "spd"
        Speed -> "spe"
    }
}