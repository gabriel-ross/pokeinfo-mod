package org.gabrielross.constants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

fun FromString(name: String): GrowthRate {
    val words = name.lowercase().split("-_ ")
    return GrowthRate.valueOf(words.joinToString(""))
}

@Serializable
enum class GrowthRate(name: String) {
    @SerialName("slow")
    Slow("slow"),

    @SerialName("medium")
    Medium("medium"),

    @SerialName("fast")
    Fast("fast"),

    @SerialName("medium-slow")
    MediumSlow("medium-slow"),

    @SerialName("slow-then-very-fast")
    Fluctuating("slow-then-very-fast"),

    @SerialName("fast-then-very-slow")
    Erratic("fast-then-very-slow")
}