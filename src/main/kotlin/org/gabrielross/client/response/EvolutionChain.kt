package org.gabrielross.client.response

import kotlinx.serialization.Serializable

@Serializable
data class EvolutionChainResponse(
    val id: Int,
    val chain: Chain
)

@Serializable
data class Chain(
    val species: ShortEntry<String>,
    val evolves_to: List<EvolvesTo>
)

@Serializable
data class EvolvesTo(
    val species: ShortEntry<String>,
    val evolves_to: List<EvolvesTo>
)