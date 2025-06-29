package org.gabrielross.api

import org.gabrielross.client.Client
import org.gabrielross.client.response.SpeciesResponse
import org.gabrielross.model.Move
import org.gabrielross.model.Pokemon
import org.gabrielross.model.SpeciesData

class Pokeinfo(
    val apiClient: Client
) {

    fun getPokemon(identifier: String): Pokemon {
        return Pokemon.fromResponse(this.apiClient.getPokemon(identifier))
    }

    fun getPokemonSpecies(identifier: String): SpeciesData {
        return SpeciesData.fromResponse(this.apiClient.getPokemonSpecies(identifier))
    }

    fun getMove(identifier: String): Move {
        return Move.fromResponse(this.apiClient.getMove(identifier))
    }

    fun getAbilityLearnset(name: String): List<String> {
        val resp = this.apiClient.getAbility(name)
        var learnset = mutableListOf<String>()
        resp.pokemon.forEach { pokemon ->
            if (pokemon.pokemon.name.contains("-gmax")) return@forEach
            learnset.add(pokemon.pokemon.name)
        }
        return learnset
    }

    // Get the names of pokemon that learn a move
    fun getMoveLearnset(name: String): List<String> {
        return getMoveLearnset(name.split("[],"))

//        val resp = this.apiClient.getMove(name)
//        var learnset = mutableListOf<String>()
//        resp.learned_by_pokemon.forEach { pokemon ->
//            // Remove Megas from learnset as they have the same learnset
//            // as their base pokemon
//            if (pokemon.name.contains("-mega")) return@forEach
//            learnset.add(pokemon.name)
//        }
//        return learnset
    }

    // Get the names of pokemon that learn all moves in names
    fun getMoveLearnset(names: List<String>): List<String> {
        if (names.isEmpty()) {
            return emptyList()
        } else if (names.size == 1) {
            return this.getMoveLearnset(names[0])
        }

        var resp = this.apiClient.getMove(names[0])
        var learnsetIntersects = mutableSetOf<String>()

        // Initialize learnset
        resp.learned_by_pokemon.forEach { pokemon ->
            // Remove Megas from learnset as they have the same learnset
            // as their base pokemon
            if (pokemon.name.contains("-mega")) return@forEach
            learnsetIntersects.add(pokemon.name)
        }

        names.drop(1).forEach { moveName ->
            var learnset = mutableSetOf<String>()
            resp = this.apiClient.getMove(moveName)
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
                if (
                    pokemon.name.contains("-mega") ||
                    pokemon.name.contains("-gmax")
                ) return@forEach

                learnset.add(pokemon.name)
            }
        }
        return learnset.toList()
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
