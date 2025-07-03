package org.gabrielross.constants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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

    override fun toString(): String = when(this) {
        Slow -> "slow"
        Medium -> "medium"
        Fast -> "fast"
        MediumSlow -> "medium-slow"
        Fluctuating -> "slow-then-very-fast"
        Erratic -> "fast-then-very-slow"
    }
}