package org.gabrielross.client.model

import kotlinx.serialization.Serializable
import org.gabrielross.constants.MoveLearnMethod
import org.gabrielross.constants.VersionGroup

@Serializable
data class PokemonResponse(
    val name: String,
    val id: Int,
    val abilities: List<Ability>,
    val moves: List<Move>,
    val species: ShortEntry<String>,
    val stats: List<Stat>,
    val types: List<PokemonType>,
    val order: Int,
    val is_default: Boolean,
)

@Serializable
data class Ability(val ability: AbilityData, val is_hidden: Boolean, val slot: Int)

@Serializable
data class AbilityData(val name: String, val url: String)

@Serializable
data class Stat(val base_stat: Int, val effort: Int, val stat: StatData)

@Serializable
data class StatData(val name: String)

@Serializable
data class PokemonType(val slot: Int, val type: Type)

@Serializable
data class Move(val move: MoveEntry, val version_group_details: List<VersionGroupDetailEntry>)

@Serializable
data class MoveEntry(val name: String, val url: String)

@Serializable
data class VersionGroupDetailEntry(
    val level_learned_at: Int,
    val move_learn_method: ShortEntry<MoveLearnMethod>,
    val version_group: ShortEntry<VersionGroup>
)
