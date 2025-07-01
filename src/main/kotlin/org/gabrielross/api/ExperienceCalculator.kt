package org.gabrielross.api

import org.gabrielross.client.Client
import org.gabrielross.constants.GrowthRate
import kotlin.math.min

var XL_CANDY_VALUE = 30000
var L_CANDY_VALUE = 10000
var M_CANDY_VALUE = 3000
var S_CANDY_VALUE = 800
var XS_CANDY_VALUE = 100

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

class ExperienceCalculator(val client: Client) {

    fun calculateCandies(
        inventory: CandyInventory = CandyInventory.max(),
        startingLevel: Int,
        targetLevel: Int,
        pokemonIdentifier: String
    ): CandyInventory {
        val data = this.client.getPokemonSpecies(pokemonIdentifier)
        return this.calculateCandies(inventory, startingLevel, targetLevel, data.growth_rate.name)
    }

    fun calculateCandies(
        inventory: CandyInventory = CandyInventory.max(),
        startingLevel: Int,
        targetLevel: Int,
        growthRate: GrowthRate
    ): CandyInventory {
        val data = this.client.getGrowthRate(growthRate.toString())
        var start = data.levels[startingLevel-1]
        var target = data.levels[targetLevel-1]

        // Search for level data if levels list is not ordered.
        if (start.level != startingLevel) {
            start = data.levels.find { levelEntry -> levelEntry.level == startingLevel }!!
        }
        if (target.level != targetLevel) {
            target = data.levels.find { levelEntry -> levelEntry.level == targetLevel }!!
        }

        var cost = CandyInventory(0,0,0,0,0)
        var xpNeeded = target.experience - start.experience

        cost.XL = min(inventory.XL, xpNeeded.floorDiv(XL_CANDY_VALUE))
        xpNeeded -= cost.XL * XL_CANDY_VALUE
        inventory.XL -= cost.XL

        cost.L = min(inventory.L, xpNeeded.floorDiv(L_CANDY_VALUE))
        xpNeeded -= cost.L * L_CANDY_VALUE
        inventory.L -= cost.L

        cost.M = min(inventory.M, xpNeeded.floorDiv(M_CANDY_VALUE))
        xpNeeded -= cost.M * M_CANDY_VALUE
        inventory.M -= cost.M

        cost.S = min(inventory.S, xpNeeded.floorDiv(S_CANDY_VALUE))
        xpNeeded -= cost.S * S_CANDY_VALUE
        inventory.S -= cost.S

        cost.S = min(inventory.S, xpNeeded.floorDiv(S_CANDY_VALUE))
        xpNeeded -= cost.S * S_CANDY_VALUE
        inventory.S -= cost.S

        if (xpNeeded > 0) {

        }


        return cost
    }
}