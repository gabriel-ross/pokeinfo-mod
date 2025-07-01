package org.gabrielross.client.response

import kotlinx.serialization.Serializable
import org.gabrielross.constants.GrowthRate

@Serializable
data class GrowthRateResponse(
    val id: Int,
    val name: GrowthRate,
    val pokemon_species: List<ShortEntry<String>>,
    val levels: List<LevelEntry>
)

@Serializable
data class LevelEntry(
    val experience: Int,
    val level: Int
)
