package org.gabrielross.pokeinfo

import org.gabrielross.client.Client
import org.gabrielross.constants.Nature
import org.gabrielross.constants.NatureModifier
import org.gabrielross.constants.Stat
import org.gabrielross.model.Stats

var XL_CANDY_VALUE = 30000
var L_CANDY_VALUE = 10000
var M_CANDY_VALUE = 3000
var S_CANDY_VALUE = 800
var XS_CANDY_VALUE = 100

class Calculator(val client: Client) {

    fun statValues(base: Stats, ivs: Stats, evs: Stats, nature: Nature, level: Int = 100): Stats {
        return Stats(
            hp = hpStatValue(base.hp, ivs.hp, evs.hp, level),
            atk = statValue(base.atk, ivs.atk, evs.atk, level, NatureModifier.lookup(nature, Stat.Attack)),
            def = statValue(base.def, ivs.def, evs.def, level, NatureModifier.lookup(nature, Stat.Defense)),
            spa = statValue(base.spa, ivs.spa, evs.spa, level, NatureModifier.lookup(nature, Stat.SpecialAttack)),
            spd = statValue(base.spd, ivs.spd, evs.spd, level, NatureModifier.lookup(nature, Stat.SpecialDefense)),
            spe = statValue(base.spe, ivs.spe, evs.spe, level, NatureModifier.lookup(nature, Stat.Speed)),
        )
    }

    fun hpStatValue(base: Int, iv: Int, ev: Int, level: Int): Int {
        return (((2 * base + iv + (ev/4)) * level) / 100) + level + 10
    }

    fun statValue(base: Int, iv: Int, ev: Int, level: Int, nature: NatureModifier): Int {
        return (((((2 * base + iv + (ev/4)) * level) / 100) + 5) * nature.value).toInt()
    }

    fun experienceRequired(pokemonIdentifier: String, startingLevel: Int, targetLevel: Int): Int {
        if (startingLevel == targetLevel) {
            return 0
        }
        val growthRate = client.getGrowthRate(client.getPokemonSpecies(pokemonIdentifier).growth_rate.toString())

        // ensure levels array is in correct order. search for level data if not
        if (growthRate.levels[startingLevel-1].level == startingLevel && growthRate.levels[targetLevel-1].level == targetLevel) {
            return growthRate.levels[targetLevel-1].experience - growthRate.levels[startingLevel-1].experience
        }
        return growthRate.levels.find { it -> it.level == targetLevel }!!.experience - growthRate.levels.find { it -> it.level == startingLevel }!!.experience
    }

    fun candies(xpRequired: Int, inventory: CandyInventory = CandyInventory.max()): CandyInventory {
        var cost = CandyInventory()

        return cost
    }
}


data class CandyInventory(
    var XL: Int = 0,
    var L: Int = 0,
    var M: Int = 0,
    var S: Int = 0,
    var XS: Int = 0
) {
    companion object {
        fun fromString(inp: String): CandyInventory {
            var inv = CandyInventory()

            return inv
        }

        fun max(): CandyInventory {
            return CandyInventory(999, 999, 999, 999, 999)
        }
    }
}

data class CandyCalculatorResponse(
    val inventory: CandyInventory,
    val xpTarget: Int,
    val xpActual: Int,
    val surplus: Int
)