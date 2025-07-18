package org.gabrielross.pokeinfo

import org.gabrielross.client.Client
import org.gabrielross.constants.GrowthRate
import kotlin.math.min




class ExperienceCalculator() {
    companion object {
        fun calculateCandies(
            xpNeeded: Int,
            inventory: CandyInventory = CandyInventory.max()
        ): CandyCalculatorResponse {
            var currentNeededXp = xpNeeded
            var cost = CandyInventory(0,0,0,0,0)

            cost.XL = min(inventory.XL, currentNeededXp.floorDiv(XL_CANDY_VALUE))
            currentNeededXp -= cost.XL * XL_CANDY_VALUE
            inventory.XL -= cost.XL

            cost.L = min(inventory.L, currentNeededXp.floorDiv(L_CANDY_VALUE))
            currentNeededXp -= cost.L * L_CANDY_VALUE
            inventory.L -= cost.L

            cost.M = min(inventory.M, currentNeededXp.floorDiv(M_CANDY_VALUE))
            currentNeededXp -= cost.M * M_CANDY_VALUE
            inventory.M -= cost.M

            cost.S = min(inventory.S, currentNeededXp.floorDiv(S_CANDY_VALUE))
            currentNeededXp -= cost.S * S_CANDY_VALUE
            inventory.S -= cost.S

            cost.XS = min(inventory.XS, currentNeededXp.floorDiv(XS_CANDY_VALUE))
            currentNeededXp -= cost.XS * XS_CANDY_VALUE
            inventory.XS -= cost.XS

            while (currentNeededXp > 0) {
                when {
                    inventory.XS > 0 -> {
                        currentNeededXp -= XS_CANDY_VALUE
                        cost.XS += 1
                        inventory.XS -= 1
                    }
                    inventory.S > 0 -> {
                        currentNeededXp -= S_CANDY_VALUE
                        cost.S += 1
                        inventory.S -= 1
                    }
                    inventory.M > 0 -> {
                        currentNeededXp -= M_CANDY_VALUE
                        cost.M += 1
                        inventory.M -= 1
                    }
                    inventory.L > 0 -> {
                        currentNeededXp -= L_CANDY_VALUE
                        cost.L += 1
                        inventory.L -= 1
                    }
                    inventory.XL > 0 -> {
                        currentNeededXp -= XL_CANDY_VALUE
                        cost.XL += 1
                        inventory.XL -= 1
                    }
                    else -> break
                }
            }

            return CandyCalculatorResponse(
                cost,
                xpNeeded,
                xpNeeded - currentNeededXp,
                -1 * currentNeededXp
            )
        }
    }

}