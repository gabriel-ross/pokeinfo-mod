package org.gabrielross.api

import org.gabrielross.client.Client
import org.gabrielross.client.response.SpeciesResponse
import org.gabrielross.model.Ability
import org.gabrielross.model.Move
import org.gabrielross.model.Pokemon
import org.gabrielross.model.SpeciesData
import kotlin.text.replace

class Pokeinfo(
    val apiClient: Client
) {

    fun calculateCandies(pokemonIdentifier: String, startLevel: Int, targetLevel: Int, candyInventory: CandyInventory = CandyInventory.max()): CandyCalculatorResponse {
        // Fetch pokemon growthrate from pokemon-species endpoint, then
        // fetch growth rate level data from growth-rate endpoint.
        val growthRateData = this.apiClient.getGrowthRate(this.apiClient.getPokemonSpecies(pokemonIdentifier).growth_rate.name.toString())
        var start = growthRateData.levels[startLevel-1]
        var target = growthRateData.levels[targetLevel-1]

        // Search for level data if levels list is not ordered.
        if (start.level != startLevel) {
            start = growthRateData.levels.find { levelEntry -> levelEntry.level == startLevel }!!
        }
        if (target.level != targetLevel) {
            target = growthRateData.levels.find { levelEntry -> levelEntry.level == targetLevel }!!
        }

        return ExperienceCalculator.calculateCandies(target.experience - start.experience, candyInventory)
    }

    fun getPokemon(identifier: String): Pokemon {
        return Pokemon.fromResponse(this.apiClient.getPokemon(identifier))
    }

    fun getPokemonSpecies(identifier: String): SpeciesData {
        return SpeciesData.fromResponse(this.apiClient.getPokemonSpecies(identifier))
    }

    fun getMove(identifier: String): Move {
        return Move.fromResponse(this.apiClient.getMove(identifier))
    }

    fun getAbility(identifier: String): Ability {
        return Ability.FromResponse(this.apiClient.getAbility(identifier))
    }

    fun getAbilityLearnset(name: String): List<String> {
        val resp = this.apiClient.getAbility(cleanNameInput(name))
        var learnset = mutableListOf<String>()
        resp.pokemon.forEach { pokemon ->
            if (pokemon.pokemon.name.contains("-gmax")) return@forEach
            learnset.add(pokemon.pokemon.name)
        }
        return learnset
    }

    // Get the names of pokemon that learn a move
    fun getMoveLearnset(names: String): List<String> {
        return getMoveLearnset(cleanPotentialListInput(names).split(","))
    }

    // Get the names of pokemon that learn all moves in names
    fun getMoveLearnset(names: List<String>): List<String> {
        if (names.isEmpty()) {
            return emptyList()
        } else if (names.size == 1) {
            return this.getMoveLearnset(cleanNameInput(names[0]))
        }

        var resp = this.apiClient.getMove(cleanNameInput(names[0]))
        var learnsetIntersects = mutableSetOf<String>()

        // Initialize learnset
        resp.learned_by_pokemon.forEach { pokemon ->
            // Filter megas & gmax pokemon as they have the same learnset as base
            if (pokemon.name.contains("-mega") || pokemon.name.contains("-gmax")) return@forEach
            learnsetIntersects.add(pokemon.name)
        }

        names.drop(1).forEach { moveName ->
            var learnset = mutableSetOf<String>()
            resp = this.apiClient.getMove(cleanNameInput(moveName))
            resp.learned_by_pokemon.forEach { pokemon ->
                if (learnsetIntersects.contains(pokemon.name)) {
                    learnset.add(pokemon.name)
                }
            }
            learnsetIntersects = learnset
        }

        return learnsetIntersects.toList()
    }

    // Get the pokemon that learn any of the listed moves
    fun GetMoveLearnsetUnion(names: List<String>): List<String> {
        var learnset = mutableSetOf<String>()
        names.forEach { name ->
            val resp = this.apiClient.getMove(name)
            resp.learned_by_pokemon.forEach { pokemon ->
                // Filter megas & gmax pokemon as they have the same learnset as base
                if (pokemon.name.contains("-mega") || pokemon.name.contains("-gmax")) return@forEach

                learnset.add(pokemon.name)
            }
        }
        return learnset.toList()
    }

    private fun cleanPotentialListInput(inp: String): String {
        return inp.filterNot{c -> c == '[' || c == ']'}
    }
    private fun cleanNameInput(inp: String): String {
        return inp.trim().replace("_", "-").replace(" ", "-")
    }

// todo
//    fun getMoveLearnset(id: Int): List<String> {
//
//    }

// todo
//    fun getMoveLearnset(ids: List<Int>): List<String> {
//
//    }
}
