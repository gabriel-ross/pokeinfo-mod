package org.gabrielross.constants

fun FromString(name: String): GrowthRate {
    val words = name.lowercase().split("-_ ")
    return GrowthRate.valueOf(words.joinToString(""))
}

enum class GrowthRate(name: String) {
    erratic("erratic"),
    fast("fast"),
    mediumFast("medium-fast"),
    mediumSlow("medium-slow"),
    slow("slow"),
    fluctuating("fluctuating")
}