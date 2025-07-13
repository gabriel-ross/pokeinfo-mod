package org.gabrielross.pokeinfo

import org.gabrielross.client.Client
import org.gabrielross.client.model.EvolutionChainResponse
import org.gabrielross.client.model.EvolvesTo
import org.gabrielross.constants.EggGroup
import org.gabrielross.constants.MoveLearnMethod
import org.gabrielross.model.Species
import java.io.IOException
import org.gabrielross.model.Pokemon as PokemonModel

class Pokemon(val client: Client) {

    // Get pokemon data. If fetching fails for the given identifier will attempt
    // to retrieve id from the /pokemon-species endpoint and then re-attempt to
    // fetch pokemon data using id.
    //
    // Accepts either pokemon name or id as identifier.
    fun get(identifier: String, detailedResponse: Boolean = false): PokemonModel {
        if (detailedResponse) {
            return try {
                PokemonModel.fromResponseData(client.getPokemon(identifier), client.getPokemonSpecies(identifier))
            } catch (e: IOException) {
                val speciesResponse = client.getPokemonSpecies(identifier)
                PokemonModel.fromResponseData(client.getPokemon(speciesResponse.id.toString()), speciesResponse)
            }
        } else {
            return try {
                PokemonModel.fromResponseData(client.getPokemon(identifier))
            } catch (e: IOException) {
                PokemonModel.fromResponseData(client.getPokemon(client.getPokemonSpecies(identifier).id.toString()))
            }
        }
    }

    fun getSpecies(identifier: String): Species {
        return Species.fromResponse(client.getPokemonSpecies(identifier))
    }

    // Returns the methods by which a pokemon can learn a move. Moves that are
    // learned upon evolution will be listed as LevelUp moves learned at level 0.
    //
    // when a Pokemon that learn a move by both evolution and level-up the
    // response.levelLearnedAt field set to the latest level at which they
    // learn the move.
    //
    // When the includePriorEvos flag is enabled this method will also
    // search the movesets of prior evolutions and results will include
    // moves that are potentially only learnable by a prior evolution
    // (spore on breloom for example).
    //
    // Note: Searching prior evolutions is a relatively expensive operation.
    fun learnsMove(
        pokemonIdentifier: String,
        moveIdentifier: String,
        includePriorEvos: Boolean = false,
        onlyIncludeLatestVersion: Boolean = true
    ): LearnableMove {
        // todo: should check if pokemon is baby and skip searching prior evos
        var canLearnMove = LearnableMove(pokemonIdentifier, moveIdentifier, false, null)
        var moves = client.getPokemon(pokemonIdentifier)
        var moveEntries = moves.moves.find { it.move.name == moveIdentifier }

        if (moveEntries != null) {
            canLearnMove.canLearnMove = true
            moveEntries.version_group_details.forEach { it ->
                if (it.move_learn_method.name == MoveLearnMethod.LevelUp) {
                    if (canLearnMove.levelLearnedAt == 0) {
                        canLearnMove.learnsByEvolution = true
                    } else {
                        canLearnMove.learnsByLevelUp = true
                    }
                    canLearnMove.levelLearnedAt = it.level_learned_at
                } else if (it.move_learn_method.name == MoveLearnMethod.Machine) {
                    canLearnMove.learnsByMachine = true
                } else if (it.move_learn_method.name == MoveLearnMethod.Egg) {
                    canLearnMove.learnsByBreeding = true
                }
            }
        } else if (includePriorEvos) {
            // todo: check prior evo moves (including egg moves)
        }

        return canLearnMove
    }


    // Return whether a pokemon is fully evolved.
    //
    // If client fails to fetch an evolution chain for the given identifier,
    // which may happen if identifier is an integer id or the name of a
    // pokemon's form, then it will attempt to fetch species name from the /pokemon endpoint.
    fun isFullyEvolved(identifier: String): Boolean {
        var name = identifier
        var queue = mutableListOf<EvolvesTo>()
        try {
            queue.add(client.makeRequest<EvolutionChainResponse>(client.getPokemonSpecies(name).evolution_chain.url).chain)
        } catch (e: IOException) {
            name = client.getPokemon(identifier).species.name
            queue.add(client.makeRequest<EvolutionChainResponse>(client.getPokemonSpecies(name).evolution_chain.url).chain)
        }

        while (!queue.isEmpty()) {
            val cur = queue.removeFirst()
            if (cur.species.name == name && cur.evolves_to.isEmpty()) {
                return true
            } else if (cur.species.name == name && !cur.evolves_to.isEmpty()) {
                return false
            }
            queue.addAll(cur.evolves_to)
        }
        return false
    }

    // Get all pokemon that share an egg group with a given pokemon.
    fun allBreedsWith(pokemonIdentifier: String): List<String> {
        var pokemon = mutableSetOf<String>()
        client.getPokemonSpecies(pokemonIdentifier).egg_groups.forEach { it ->
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


data class LearnableMove(
    val pokemon: String,
    val move: String,
    var canLearnMove: Boolean,
    var levelLearnedAt: Int?,
    var learnsByLevelUp: Boolean = false,
    var learnsByEvolution: Boolean = false,
    var learnsByMachine: Boolean = false,
    var learnsByBreeding: Boolean = false,
    var learnsByPriorEvolution: Boolean = false,
    var priorEvoLearnMethod: LearnableMove? = null
) {
    override fun toString(): String {
        if (!canLearnMove) {
            return "$pokemon cannot learn $move by any known methods"
        }
        return """
            pokemon: $pokemon
            move: $move
            learns at level: $levelLearnedAt
            learns via level-up: $learnsByLevelUp
            learns upon evolution: $learnsByEvolution
            learns from tm: $learnsByMachine
            learns via egg move: $learnsByBreeding
            learns from prior evolution: $learnsByPriorEvolution
           prior evolution learn method: $priorEvoLearnMethod
        """.trimIndent()
    }
}