package org.gabrielross.model

import org.gabrielross.client.response.PokemonResponse
import org.gabrielross.constants.EggGroup
import org.gabrielross.constants.GrowthRate
import org.gabrielross.constants.MoveLearnMethod
import org.gabrielross.constants.Type

// Creates a Pokemon object from a PokemonResponse object containing data
// from an API response for pokemon
fun PokemonFromAPIResponse(data: PokemonResponse): Pokemon {
    var abilities = mutableListOf<PokemonAbility>()
    data.abilities.forEach { ability ->
        abilities.add(PokemonAbility(ability.ability.name, ability.is_hidden))
    }

    var type1: Type = Type.normal
    var type2: Type? = null
    data.types.forEach { type ->
        if (type.slot == 1) {
            type1 = type.type.name
        } else if (type.slot == 2) {
            type2 = type.type.name
        }
    }

    var baseStats = Stats()
    var evYield = Stats()
    data.stats.forEach { stat ->
        when (stat.stat.name) {
            "hp" -> {
                baseStats.hp = stat.base_stat
                evYield.hp = stat.effort
            }

            "attack" -> {
                baseStats.atk = stat.base_stat
                evYield.atk = stat.effort
            }

            "defense" -> {
                baseStats.def = stat.base_stat
                evYield.def = stat.effort
            }

            "special-attack" -> {
                baseStats.spa = stat.base_stat
                evYield.spa = stat.effort
            }

            "special-defense" -> {
                baseStats.spd = stat.base_stat
                evYield.spd = stat.effort
            }

            "speed" -> {
                baseStats.spe = stat.base_stat
                evYield.spe = stat.effort
            }

        }
    }

    return Pokemon(
        id = data.id,
        name = data.name,
        baseStats = baseStats,
        evYield = evYield,
        abilities = abilities,
        moves = emptyList<PokemonMove>(),
        type1 = type1,
        type2 = type2,
        // todo: fix these later
        eggGroup1 = EggGroup.noEggsDiscovered,
        eggGroup2 = null,
        growthRate = GrowthRate.erratic
    )
}

// Contains data for a pokemon object
data class PokemonData(
    val id: Int,
    val name: String,
    val baseStats: Stats,
    val abilities: List<PokemonAbilityData> = emptyList<PokemonAbilityData>(),
    val moves: List<PokemonMoveData> = emptyList<PokemonMoveData>(),
    val evYield: Stats = Stats(),
    val type1: Type,
    val type2: Type?,
    val eggGroup1: EggGroup,
    val eggGroup2: EggGroup?,
    val growthRate: GrowthRate
)

class Pokemon(
    val id: Int,
    val name: String,
    val baseStats: Stats,
    val abilities: List<PokemonAbility> = emptyList<PokemonAbility>(),
    val moves: List<PokemonMove> = emptyList<PokemonMove>(),
    val evYield: Stats = Stats(),
    val type1: Type,
    val type2: Type?,
    val eggGroup1: EggGroup,
    val eggGroup2: EggGroup?,
    val growthRate: GrowthRate
) {
    fun Data(): PokemonData {
        var abilities = mutableListOf<PokemonAbilityData>()
        this.abilities.forEach { ability ->
            abilities.add(ability.Data())
        }
        var moves = mutableListOf<PokemonMoveData>()
        this.moves.forEach { move ->
            moves.add(move.Data())
        }
        return PokemonData(
            id = this.id,
            name = this.name,
            baseStats = this.baseStats,
            abilities = abilities,
            moves = moves,
            evYield = this.evYield,
            type1 = this.type1,
            type2 = this.type2,
            eggGroup1 = this.eggGroup1,
            eggGroup2 = this.eggGroup2,
            growthRate = this.growthRate,
        )
    }
}

data class PokemonAbilityData(
    val name: String,
    val isHidden: Boolean
)

class PokemonAbility(
    val name: String,
    val isHidden: Boolean
) {
    fun Data(): PokemonAbilityData {
        return PokemonAbilityData(
            name = this.name,
            isHidden = this.isHidden
        )
    }
}

data class PokemonMoveData(
    val name: String,
    val learnMethod: MoveLearnMethod
)

class PokemonMove(
    val name: String,
    val learnMethod: MoveLearnMethod
) {
    fun Data(): PokemonMoveData {
        return PokemonMoveData(
            name = this.name,
            learnMethod = this.learnMethod
        )
    }
}
