package org.gabrielross.pokeinfo

import org.gabrielross.client.Client
import org.gabrielross.client.model.PokemonResponse
import org.gabrielross.constants.Type

class filters(
    includeGmax: Boolean = false,
    includeMega: Boolean = false,
    includeNFE: Boolean = true
) {
    val includeGmax: Boolean = false
    val includeMega: Boolean = false
    val includeNFE: Boolean = true
}

class Search(val client: Client) {
    val pokemon: Pokemon = Pokemon(client)

    fun pokemon(type: Type? = null, ability: String, moves: String, filters: filters = filters()): List<String> {
        return pokemon(type, ability, cleanPotentialListInput(moves).split(","), filters)
    }

    fun pokemon(type: Type? = null, ability: String, moves: List<String>, filters: filters = filters()): List<String> {
        if (ability.isEmpty() || moves.isEmpty()) {
            return emptyList()
        }
        var learnset = fetchFilteredAbilitySet(ability, filters)
        moves.forEach { it ->
            learnset = learnset.intersect(fetchFilteredLearnset(it, filters))
        }

        if (type != null) {
            learnset = learnset.intersect(fetchFilteredTypeSet(type, filters))
        }

        return learnset.toList()
    }

    fun haveAbility(identifier: String, filters: filters = filters(includeMega = true)): List<String> {
        return fetchFilteredAbilitySet(identifier, filters).toList()
    }

    fun moveLearnsetIntersect(identifiers: String, filters: filters = filters()): List<String> {
        return moveLearnsetIntersect(cleanPotentialListInput(identifiers).split(","), filters)
    }

    fun moveLearnsetIntersect(identifiers: List<String>, filters: filters = filters()): List<String> {
        if (identifiers.isEmpty()) {
            return emptyList()
        }
        var learnset = fetchFilteredLearnset(identifiers[0], filters)
        if (identifiers.size == 1) {
            return learnset.toList()
        }

        identifiers.drop(1).forEach { it ->
            learnset = learnset.intersect(fetchFilteredLearnset(it, filters))
        }

        return learnset.toList()
    }


    fun moveLearnsetUnion(identifiers: String, filters: filters = filters()): List<String> {
        return moveLearnsetUnion(cleanPotentialListInput(identifiers).split(","), filters)
    }

    fun moveLearnsetUnion(identifiers: List<String>, filters: filters = filters()): List<String> {
        if (identifiers.isEmpty()) {
            return emptyList()
        }
        var learnset = fetchFilteredLearnset(identifiers[0], filters)
        if (identifiers.size == 1) {
            return learnset.toList()
        }

        identifiers.drop(1).forEach { it ->
            learnset = learnset.union(fetchFilteredLearnset(it, filters))
        }

        return learnset.toList()
    }

    private fun fetchFilteredTypeSet(
        identifer: Type,
        filters: filters = filters(
            includeMega = false,
            includeGmax = false
        )): Set<String> {
        var typeset = mutableSetOf<String>()
        client.getType(identifer).pokemon.forEach { it ->
            when {
                it.pokemon.name.contains("-gmax") && filters.includeGmax -> typeset.add(it.pokemon.name)
                it.pokemon.name.contains("-mega") && filters.includeMega -> typeset.add(it.pokemon.name)
                filters.includeNFE -> typeset.add(it.pokemon.name)
                else -> {
                    val species = client.makeRequest<PokemonResponse>(it.pokemon.url).species.name
                    if (pokemon.isFullyEvolved(species)) {
                        typeset.add(it.pokemon.name)
                    }
                }
            }
        }
        return typeset
    }

    private fun fetchFilteredAbilitySet(
        identifer: String,
        filters: filters = filters(
            includeMega = false,
            includeGmax = false
        )): Set<String> {
        var learnset = mutableSetOf<String>()
        client.getAbility(cleanNameInput(identifer)).pokemon.forEach { it ->
            when {
                it.pokemon.name.contains("-gmax") && filters.includeGmax -> learnset.add(it.pokemon.name)
                it.pokemon.name.contains("-mega") && filters.includeMega -> learnset.add(it.pokemon.name)
                filters.includeNFE -> learnset.add(it.pokemon.name)
                else -> {
                    val species = client.makeRequest<PokemonResponse>(it.pokemon.url).species.name
                    if (pokemon.isFullyEvolved(species)) {
                        learnset.add(it.pokemon.name)
                    }
                }
            }
        }
        return learnset
    }

    private fun fetchFilteredLearnset(
        identifer: String,
        filters: filters = filters(
            includeMega = false,
            includeGmax = false
        )): Set<String> {
        var learnset = mutableSetOf<String>()
        client.getMove(cleanNameInput(identifer)).learned_by_pokemon.forEach { it ->
            when {
                it.name.contains("-gmax") && filters.includeGmax -> learnset.add(it.name)
                it.name.contains("-mega") && filters.includeMega -> learnset.add(it.name)
                filters.includeNFE -> learnset.add(it.name)
                else -> {
                    val species = client.makeRequest<PokemonResponse>(it.url).species.name
                    if (pokemon.isFullyEvolved(species)) {
                        learnset.add(it.name)
                    }
                }
            }
        }
        return learnset
    }


}
