package org.gabrielross.pokeinfo

import org.gabrielross.client.Client
import org.gabrielross.client.model.PokemonResponse
import org.gabrielross.constants.Type

class filters(
    includeGmax: Boolean = false,
    includeMega: Boolean = false,
    includeNFE: Boolean = false
) {
    val includeGmax: Boolean = false
    val includeMega: Boolean = false
    val includeNFE: Boolean = false
}

class Search(val client: Client) {
    val pokemon: Pokemon = Pokemon(client)

    // Overload method for fetching pokemon from a raw input argument.
    fun pokemon(args: String): List<String> {
        if (args.isEmpty()) {
            return emptyList()
        }

        val extracted = args.lowercase().split(" ").map { it.split("=") }.associate { it.first() to it.last() }

        var pokemon = emptySet<String>()
        if (extracted.contains("ability")) {
            pokemon = fetchAbilityset(extracted["ability"]!!)
        }

        // Extact move arguments
        var moveArgVal = extracted["move"]
        if (moveArgVal == null) {
            moveArgVal = extracted["moves"]
        }
        moveArgVal?.split(",")?.forEach {
            val learnset = fetchLearnset(it)
            if (pokemon.isEmpty()) {
                pokemon = learnset
            } else {
                pokemon = pokemon.intersect(learnset)
            }
        }

        // Extract type arguments
        var typeArgVal = extracted["type"]
        if (typeArgVal == null) {
            typeArgVal = extracted["types"]
        }
        typeArgVal?.split(",")?.take(2)?.forEach {
            val typeset = fetchTypeset(it)
            if (pokemon.isEmpty()) {
                pokemon = typeset
            } else {
                pokemon = pokemon.intersect(typeset)
            }
        }

        return filterPokemonset(pokemon, filters(
            includeGmax = extracted["--includegmax"].toBoolean(),
            includeMega = extracted["--includemega"].toBoolean(),
            includeNFE = extracted["--includenfe"].toBoolean()
        )).toList()
    }

    fun pokemon(ability: String = "", moves: String = "", types: List<Type> = emptyList(), filters: filters = filters()): List<String> {
        return pokemon(ability, cleanPotentialListInput(moves).split(","), types, filters)
    }

    fun pokemon(ability: String, moves: List<String>, types: List<Type>, filters: filters = filters()): List<String> {
        if (types.isEmpty() && ability.isEmpty() && moves.isEmpty()) {
            return emptyList()
        }

        var pokemon = emptySet<String>()
        if (!ability.isEmpty()) {
            pokemon = fetchAbilityset(ability)
        }
        moves.forEach {
            pokemon = pokemon.intersect(fetchLearnset(it))
        }
        types.take(2).forEach {
            pokemon = pokemon.intersect(fetchTypeset(it))
        }

        return filterPokemonset(pokemon, filters).toList()
    }

    fun haveAbility(identifier: String, filters: filters = filters(includeMega = true)): List<String> {
        return filterPokemonset(fetchAbilityset(identifier), filters).toList()
    }

    fun learnsMoves(identifiers: String, intersects: Boolean = true, filters: filters = filters()): List<String> {
        return learnsMoves(cleanPotentialListInput(identifiers).split(","), intersects, filters)
    }

    fun learnsMoves(identifiers: List<String>, intersects: Boolean, filters: filters = filters()): List<String> {
        if (identifiers.isEmpty()) {
            return emptyList()
        }
        var learnset = fetchLearnset(identifiers[0])
        if (identifiers.size == 1) {
            return learnset.toList()
        }

        if (intersects) {
            identifiers.drop(1).forEach { it ->
                learnset = learnset.intersect(fetchLearnset(it))
            }
        } else {
            identifiers.drop(1).forEach { it ->
                learnset = learnset.union(fetchLearnset(it))
            }
        }

        return filterPokemonset(learnset, filters).toList()
    }

    private fun filterPokemonset(pokemon: Set<String>, filters: filters): Set<String> {
        var filtered = mutableSetOf<String>()
        pokemon.forEach {
            when {
                it.contains("-gmax") && filters.includeGmax -> filtered.add(it)
                it.contains("-mega") && filters.includeMega -> filtered.add(it)
                filters.includeNFE -> filtered.add(it)
                this.pokemon.isFullyEvolved(it) -> filtered.add(it)
            }
        }
        return filtered
    }
    private fun fetchAbilityset(identifier: String): Set<String> {
        var haveAbility = mutableSetOf<String>()
        client.getAbility(identifier).pokemon.forEach { haveAbility.add(it.pokemon.name) }
        return haveAbility
    }
    private fun fetchLearnset(identifier: String): Set<String> {
        var haveAbility = mutableSetOf<String>()
        client.getMove(identifier).learned_by_pokemon.forEach { haveAbility.add(it.name) }
        return haveAbility
    }
    private fun fetchTypeset(type: String): Set<String> {
        return fetchTypeset(Type.valueOf(type))
    }
    private fun fetchTypeset(type: Type): Set<String> {
        var haveType = mutableSetOf<String>()
        client.getType(type).pokemon.forEach { haveType.add(it.pokemon.name) }
        return haveType
    }
}
