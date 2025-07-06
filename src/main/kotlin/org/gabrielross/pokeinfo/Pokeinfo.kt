package org.gabrielross.pokeinfo

import org.gabrielross.client.Client
import org.gabrielross.client.model.EvolutionChainResponse
import org.gabrielross.client.model.EvolvesTo
import org.gabrielross.constants.EggGroup
import org.gabrielross.constants.MoveLearnMethod
import org.gabrielross.constants.NatureModifier
import org.gabrielross.model.Pokemon as PokemonModel
import org.gabrielross.model.Species
import java.io.IOException
import kotlin.text.replace

class Pokeinfo(val client: Client) {
    val pokemon: Pokemon = Pokemon(client)
    val ability: Ability = Ability(client)
    val move: Move = Move(client)
    val search: Search = Search(client)

//    fun calculateCandies(pokemonIdentifier: String, startLevel: Int, targetLevel: Int, candyInventory: CandyInventory = CandyInventory.max()): CandyCalculatorResponse {
//        // Fetch pokemon growthrate from pokemon-species endpoint, then
//        // fetch growth rate level data from growth-rate endpoint.
//        val growthRateData = this.apiClient.getGrowthRate(this.apiClient.getPokemonSpecies(pokemonIdentifier).growth_rate.toString())
//        var start = growthRateData.levels[startLevel-1]
//        var target = growthRateData.levels[targetLevel-1]
//
//        // Search for level data if levels list is not ordered.
//        if (start.level != startLevel) {
//            start = growthRateData.levels.find { levelEntry -> levelEntry.level == startLevel }!!
//        }
//        if (target.level != targetLevel) {
//            target = growthRateData.levels.find { levelEntry -> levelEntry.level == targetLevel }!!
//        }
//
//        return ExperienceCalculator.calculateCandies(target.experience - start.experience, candyInventory)
//    }

    fun calculateHP(base: Int, iv: Int, ev: Int, level: Int): Int {
        return (((2 * base + iv + (ev/4)) * level) / 100) + level + 10
    }

    fun calculateStat(base: Int, iv: Int, ev: Int, level: Int, nature: NatureModifier): Int {
        return (((((2 * base + iv + (ev/4)) * level) / 100) + 5) * nature.value).toInt()
    }

    fun natureDoes(identifier: String): String {
        val nature = client.getNature(identifier)
        return "$identifier: +${nature.increased_stat.name} -${nature.decreased_stat.name}"
    }

}
