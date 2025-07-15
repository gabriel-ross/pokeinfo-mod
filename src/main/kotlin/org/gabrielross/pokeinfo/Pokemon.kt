package org.gabrielross.pokeinfo

import org.gabrielross.client.Client
import org.gabrielross.client.model.EvolutionChainResponse
import org.gabrielross.client.model.EvolvesTo
import org.gabrielross.client.model.PokemonResponse
import org.gabrielross.constants.EggGroup
import org.gabrielross.constants.MoveLearnMethod
import org.gabrielross.constants.VersionGroup
import org.gabrielross.model.Species
import java.io.IOException
import org.gabrielross.model.Pokemon as PokemonModel

private val VALID_FORM_REGIONS = setOf("alola", "galar", "hisui")

class Pokemon(val client: Client) {

    // Get pokemon data. If fetching fails for the given identifier will attempt
    // to retrieve id from the /pokemon-species endpoint and then re-attempt to
    // fetch pokemon data using id.
    //
    // Accepts either pokemon name or id as identifier.
    fun get(identifier: String, detailedResponse: Boolean = false): PokemonModel {
        if (detailedResponse) {
                return PokemonModel.fromResponseData(get(identifier), client.getPokemonSpecies(identifier))
        } else {
            return PokemonModel.fromResponseData(get(identifier), null)
        }
    }

    // Helper function for fetching pokemon data.
    // If fetching fails for the given identifier will attempt
    // to retrieve id from the /pokemon-species endpoint and then
    // re-attempt to fetch pokemon data using id.
    private fun get(identifier: String): PokemonResponse {
        return try {
            client.getPokemon(identifier)
        } catch (e: IOException) {
            val speciesResponse = client.getPokemonSpecies(identifier)
            client.getPokemon(speciesResponse.id.toString())
        }
    }

    // Get only pokemon species data
    fun getSpecies(identifier: String): Species {
        return Species.fromResponse(client.getPokemonSpecies(identifier))
    }

    // Returns the methods by which a pokemon can learn a move.
    //
    // when a Pokemon that learn a move by both evolution and level-up the
    // levelLearnedAt field set to the latest level at which they learn the move.
    //
    // Note: There are some known data gaps when it comes to egg moves of certain
    // pokemon - likely due to the introduction of the mirror herb.
    fun canLearn(
        pokemon: String,
        move: String,
        includePriorEvos: Boolean = true,
        onlyIncludeLatestVersion: Boolean = true
    ): LearnableMove {
        val learnMethods = LearnableMove(pokemon, move, false, null)
        val pokemonData = get(pokemon)
        val learnEntries = pokemonData.moves.find { it.move.name == move }
        if (learnEntries != null) {
            learnMethods.canLearnMove = true
            learnEntries.version_group_details.forEach { it ->
                when (it.move_learn_method.name) {
                    MoveLearnMethod.LevelUp -> {
                        if (learnMethods.levelLearnedAt == 0) {
                            learnMethods.learnsByEvolution = LearnMethod(true, it.version_group.name)
                        } else {
                            learnMethods.learnsByLevelUp = LearnMethod(true, it.version_group.name)
                        }
                    }
                    MoveLearnMethod.Machine -> learnMethods.learnsByMachine = LearnMethod(true, it.version_group.name)
                    MoveLearnMethod.Egg -> learnMethods.learnsByBreeding = LearnMethod(true, it.version_group.name)
                    MoveLearnMethod.Tutor -> learnMethods.learnsByTutor = LearnMethod(true, it.version_group.name)
                    else -> return@forEach
                }
            }
        }
        return learnMethods
    }

    // Helper method for extracting the ways in which a pokemon can learn a move
    // from a PokemonResponse
    private fun learnMethods(pokemonData: PokemonResponse, pokemonName: String, move: String): LearnableMove {
        val learnMethods = LearnableMove(pokemonName, move, false, null)
        val learnEntries = pokemonData.moves.find { it.move.name == move }
        if (learnEntries != null) {
            learnMethods.canLearnMove = true
            learnEntries.version_group_details.forEach { it ->
                when (it.move_learn_method.name) {
                    MoveLearnMethod.LevelUp -> {
                        if (learnMethods.levelLearnedAt == 0) {
                            learnMethods.learnsByEvolution = LearnMethod(true, it.version_group.name)
                        } else {
                            learnMethods.learnsByLevelUp = LearnMethod(true, it.version_group.name)
                        }
                    }
                    MoveLearnMethod.Machine -> learnMethods.learnsByMachine = LearnMethod(true, it.version_group.name)
                    MoveLearnMethod.Egg -> learnMethods.learnsByBreeding = LearnMethod(true, it.version_group.name)
                    MoveLearnMethod.Tutor -> learnMethods.learnsByTutor = LearnMethod(true, it.version_group.name)
                    else -> return@forEach
                }
            }
        }
        return learnMethods
    }

    // Helper function for determining if a pokemon name is a regional form
    private fun isRegionalForm(pokemon: String): Boolean {
        val nameDeconstructed = pokemon.split("-")
        if (nameDeconstructed.size == 1) {
            return false
        } else if (VALID_FORM_REGIONS.contains(nameDeconstructed.last())) {
            return true
        }
        return false
    }

    // Return whether a pokemon is fully evolved.
    //
    // If client fails to fetch an evolution chain for the given identifier,
    // which may happen if identifier is an integer id or the name of a
    // pokemon's form, then it will attempt to fetch species name from the /pokemon endpoint.
    fun isFullyEvolved(identifier: String): Boolean {
        // Check for unique forms of the same species that exist outside of
        // the species' evolution chain (ex. ursaluna-bloodmoon)
        val pkResp = client.getPokemon(identifier)
        if (pkResp.order == -1 && pkResp.is_default) {
            return true
        }

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
        val pk1 = client.getPokemonSpecies(it)
        val pk2 = client.getPokemonSpecies(other)
        if (pk1.egg_groups.isEmpty() ||
            pk1.egg_groups[0].name == EggGroup.NoEggs ||
            pk2.egg_groups.isEmpty() ||
            pk2.egg_groups[0].name == EggGroup.NoEggs
        ) {
            return false
        }


        val dittoName = "ditto"
        val dittoId = "132"
        if (it == dittoName ||
            it == dittoId ||
            other == dittoName ||
            other == dittoId) {
            return true
        }

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
    var canLearnMove: Boolean = false,
    var levelLearnedAt: Int? = null,
    var learnsByLevelUp: LearnMethod = LearnMethod(false),
    var learnsByEvolution: LearnMethod = LearnMethod(false),
    var learnsByMachine: LearnMethod = LearnMethod(false),
    var learnsByBreeding: LearnMethod = LearnMethod(false),
    var learnsByTutor: LearnMethod = LearnMethod(false),
    var learnsByPriorEvolution: LearnMethod = LearnMethod(false),
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

data class LearnMethod(
    var learnable: Boolean = false,
    var latestGameLearnedIn: VersionGroup? = null
)