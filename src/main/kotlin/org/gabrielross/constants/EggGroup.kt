package org.gabrielross.constants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class EggGroup() {
    @SerialName("mineral")
    Mineral,

    @SerialName("amorphous")
    Amorphous,

    @SerialName("grass")
    Grass,

    @SerialName("water1")
    Water1,

    @SerialName("water2")
    Water2,

    @SerialName("water3")
    Water3,

    @SerialName("bug")
    Bug,

    @SerialName("dragon")
    Dragon,

    @SerialName("flying")
    Flying,

    @SerialName("field")
    Field,

    @SerialName("human-like")
    HumanLike,

    @SerialName("fairy")
    Fairy,

    @SerialName("monster")
    Monster,

    @SerialName("ditto")
    Ditto,

    @SerialName("no-eggs-discovered")
    NoEggsDiscovered,

    // The following are previous names of egg groups.
    // Corresponds to: Field
    @SerialName("ground")
    Ground,

    // Corresponds to: Grass
    @SerialName("plant")
    Plant,

    // Corresponds to: HumanLike
    @SerialName("humanshape")
    HumanShape,

    // Corresponds to Amorphous
    @SerialName("indeterminate")
    Indeterminate,

    // Corresponds to: NoEggsDiscovered
    @SerialName("no-eggs")
    NoEggs;

    override fun toString(): String = when(this) {
        Mineral -> "mineral"
        Amorphous -> "amorphous"
        Grass -> "grass"
        Water1 -> "water1"
        Water2 -> "water2"
        Water3 -> "water3"
        Bug -> "bug"
        Dragon -> "dragon"
        Flying -> "flying"
        Field -> "field"
        HumanLike -> "human-like"
        Fairy -> "fairy"
        Monster -> "monster"
        Ditto -> "ditto"
        NoEggsDiscovered -> "no-eggs-discovered"
        Ground -> "ground"
        Plant -> "plant"
        HumanShape -> "humanshape"
        NoEggs -> "no-eggs"
        Indeterminate -> "indeterminate"
    }
}