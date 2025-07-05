package org.gabrielross.pokeinfo

import org.gabrielross.client.Client
import org.gabrielross.client.model.EvolutionChainResponse
import org.gabrielross.client.model.EvolvesTo
import org.gabrielross.constants.EggGroup
import org.gabrielross.model.Species
import java.io.IOException
import org.gabrielross.model.Pokemon as PokemonModel

class Pokemon(val client: Client) {

    // Get pokemon data. If fetching fails for the given identifier will attempt
    // to retrieve id from the /pokemon-species endpoint and then re-attempt to
    // fetch pokemon data using id.
    //
    // Accepts either pokemon name or id as identifier.
    fun get(identifier: String): PokemonModel {
        return try {
            PokemonModel.fromResponseData(client.getPokemon(identifier))
        } catch (e: IOException) {
            PokemonModel.fromResponseData(client.getPokemon(client.getPokemonSpecies(identifier).id.toString()))
        }
    }

//    fun search(abilityIdentifier: String, moves: String, onlyFullyEvolved: Boolean = false): List<String> {
//        return search(abilityIdentifier, cleanPotentialListInput(moves).split(","), onlyFullyEvolved)
//    }
//
//    // Lookup pokemon that have access to ability and learn the provided moves.
//    fun search(abilityIdentifier: String, moves: List<String>, onlyFullyEvolved: Boolean = false): List<String> {
//        if (abilityIdentifier.isEmpty() || moves.isEmpty()) {
//            return emptyList()
//        }
//        var learnset = abilityLearnset(ability)
//        moves.forEach { mv ->
//            var intersects = mutableSetOf<String>()
//            client.getMove(Pokeinfo.Companion.cleanNameInput(mv)).learned_by_pokemon.forEach { pk ->
//                if (learnset.contains(pk.name)) {
//                    intersects.add(pk.name)
//                }
//            }
//            learnset = intersects
//        }
//
//        if (onlyFullyEvolved) {
//            return learnset.filter { it -> isFullyEvolved(it) }
//        }
//        return learnset.toList()
//    }

    fun getSpecies(identifier: String): Species {
        return Species.fromResponse(client.getPokemonSpecies(identifier))
    }


    // Return whether a pokemon is fully evolved
    fun isFullyEvolved(pokemon: String): Boolean {
        var queue = mutableListOf<EvolvesTo>()
        queue.add(client.makeRequest<EvolutionChainResponse>(client.getPokemonSpecies(pokemon).evolution_chain.url).chain)
        while (!queue.isEmpty()) {
            val cur = queue.removeFirst()
            if (cur.species.name == pokemon && cur.evolves_to.isEmpty()) {
                return true
            } else if (cur.species.name == pokemon && !cur.evolves_to.isEmpty()) {
                return false
            }
            queue.addAll(cur.evolves_to)
        }
        return false
    }

    // Get all pokemon that share an egg group with a given pokemon.
    fun allBreedsWith(pokemonIdentifier: String): List<String> {
        var pokemon = mutableSetOf<String>()
        val speciesResp = client.getPokemonSpecies(pokemonIdentifier).egg_groups.forEach { it ->
            client.getEggGroup(it.name.toString()).pokemon_species.forEach { pk ->
                pokemon.add(pk.name)
            }
        }

        return pokemon.toList()
    }

    // Checks whether two pokemon share an egg group. Returns false if either
    // of the pokemon belong to any of the following egg groups:
    // - NoEggs/NoEggsDiscovered
    // - Indeterminate
    //
    // Returns true if either of the pokemon are ditto or if they share any
    // egg groups.
    fun canBreed(it: String, other: String): Boolean {
        val dittoName = "ditto"
        val dittoId = "132"
        if (it == dittoName ||
            it == dittoId ||
            other == dittoName ||
            other == dittoId) {
            return true
        }
        val pk1 = client.getPokemonSpecies(it)
        val pk2 = client.getPokemonSpecies(other)
        pk1.egg_groups.forEach { eg1 ->
            pk2.egg_groups.forEach { eg2 ->
                // Return true if either pokemon is ditto
                if (eg1.name == EggGroup.NoEggs ||
                    eg2.name == EggGroup.NoEggs ||
                    eg1.name == EggGroup.Indeterminate ||
                    eg2.name == EggGroup.Indeterminate) {
                    return false
                } else if (eg1.name == eg2.name) {
                    return true
                }
            }
        }

        return false
    }
}