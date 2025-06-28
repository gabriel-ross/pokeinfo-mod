package org.gabrielross.client.response

import kotlinx.serialization.Serializable

@Serializable
data class EggGroupResponse(
    val id: Int,
    val name: String,
    val pokemon_species: List<ShortEntry<String>>
)
