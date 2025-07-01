package org.gabrielross.constants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

fun FromString(name: String): GrowthRate {
    val words = name.lowercase().split("-_ ")
    return GrowthRate.valueOf(words.joinToString(""))
}

@Serializable
enum class GrowthRate() {

    @SerialName("slow")
    Slow,

    @SerialName("medium")
    Medium,

    @SerialName("fast")
    Fast,

    @SerialName("medium-slow")
    MediumSlow,

    @SerialName("slow-then-very-fast")
    Fluctuating,

    @SerialName("fast-then-very-slow")
    Erratic;

    companion object {
        fun valueOf(g: GrowthRate): String {
            when (g) {
                Slow -> "slow"
                Medium -> "medium"
                Fast -> "fast"
                MediumSlow -> "medium-slow"
                Fluctuating -> "slow-then-very-fast"
                Erratic -> "fast-then-very-slow"
            }
            return ""
        }
    }
}