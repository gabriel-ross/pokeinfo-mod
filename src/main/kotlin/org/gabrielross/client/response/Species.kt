package org.gabrielross.client.model

import kotlinx.serialization.Serializable
import org.gabrielross.constants.EggGroup
import org.gabrielross.constants.GrowthRate

@Serializable
data class SpeciesResponse(
    val id: Int,
    val name: String,
    val capture_rate: Int,
    val evolves_from_species: ShortEntry<String>?,
    val evolution_chain: EvolutionChain,
    val egg_groups: List<ShortEntry<EggGroup>>,
    val growth_rate: SpeciesGrowthRate,
    val pokedex_numbers: List<PokedexEntry>
)

@Serializable
data class EvolutionChain(val url: String)

@Serializable
data class SpeciesGrowthRate(
    val name: GrowthRate,
    val url: String
)

@Serializable
data class PokedexEntry(
    val entry_number: Int,
    val pokedex: ShortEntry<String>
)
