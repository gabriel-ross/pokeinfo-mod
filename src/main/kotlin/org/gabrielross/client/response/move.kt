package org.gabrielross.client.response

import kotlinx.serialization.Serializable
import org.gabrielross.constants.DamageClass
import org.gabrielross.constants.VersionGroup

@Serializable
data class MoveResponse(
    val id: Int,
    val name: String,
    val accuracy: Int?,
    val power: Int?,
    val pp: Int,
    val type: Type,
    val damage_class: DamageClassEntry,
    val effect_entries: List<EffectEntry>,
    val learned_by_pokemon: List<LearnedByPokemon>,
    val machines: List<Machine>
)

@Serializable
data class LearnedByPokemon(val name: String, val url: String)

@Serializable
data class DamageClassEntry(val name: DamageClass, val url: String)

@Serializable
data class Machine(val machine: MachineEntry, val version_group: ShortEntry<VersionGroup>)

@Serializable
data class MachineEntry(val url: String)