package org.gabrielross.model

import org.gabrielross.client.model.PokemonResponse
import org.gabrielross.client.model.SpeciesResponse
import org.gabrielross.constants.EggGroup
import org.gabrielross.constants.GrowthRate
import org.gabrielross.constants.MoveLearnMethod
import org.gabrielross.constants.Type


// Contains data for a pokemon object
data class Pokemon(
    val id: Int,
    val name: String,
    val baseStats: Stats,
    val abilities: List<PokemonAbility> = emptyList<PokemonAbility>(),
    val moves: List<PokemonMove> = emptyList<PokemonMove>(),
    val evYield: Stats = Stats(),
    val type1: Type,
    val type2: Type?,
    val speciesData: Species?
) {
    companion object {
        fun fromResponseData(data: PokemonResponse): Pokemon {
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
                speciesData = null
            )
        }
    }
}

data class Species(
    val id: Int,
    val name: String,
    val captureRate: Int,
    val eggGroup1: EggGroup,
    val eggGroup2: EggGroup?,
    val growthRate: GrowthRate,
) {
    companion object {
        fun fromResponse(data: SpeciesResponse): Species {
            val primaryEggGroup = data.egg_groups[0].name
            var secondaryEggGroup: EggGroup? = null
            if (data.egg_groups.size > 1) {
                secondaryEggGroup = data.egg_groups[1].name
            }
            return Species(
                id = data.id,
                name = data.name,
                captureRate = data.capture_rate,
                eggGroup1 = primaryEggGroup,
                eggGroup2 = secondaryEggGroup,
                growthRate = data.growth_rate.name
            )
        }
    }
}


data class PokemonAbility(
    val name: String,
    val isHidden: Boolean
)

data class PokemonMove(
    val name: String,
    val learnMethod: MoveLearnMethod
)


