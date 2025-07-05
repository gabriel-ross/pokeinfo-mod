package org.gabrielross.client.model

import kotlinx.serialization.Serializable

@Serializable
data class EvolutionChainResponse(
    val id: Int,
    val chain: EvolvesTo
)

@Serializable
data class EvolvesTo(
    val species: ShortEntry<String>,
    val evolves_to: List<EvolvesTo>
)