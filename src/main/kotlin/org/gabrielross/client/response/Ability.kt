package org.gabrielross.client.response

import kotlinx.serialization.Serializable

@Serializable
data class AbilityResponse(
    val id: Int,
    val name: String,
    val effect_entries: List<EffectEntry>,
    val pokemon: List<Pokemon>
)

@Serializable
data class Pokemon(val is_hidden: Boolean, val pokemon: PokemonEntry, val slot: Int)

@Serializable
data class PokemonEntry(val name: String, val url: String)
