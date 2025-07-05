package org.gabrielross.pokeinfo

import org.gabrielross.client.Client
import org.gabrielross.client.model.PokemonResponse

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
    fun haveAbility(identifier: String, filters: filters = filters(includeMega = true)): List<String> {
        var learnset = mutableSetOf<String>()
        client.getAbility(cleanNameInput(identifier)).pokemon.forEach { it ->
            // todo: switch to if/else, figure out including forms (like ursaluna-bloodmoon)
            when {
                it.pokemon.name.contains("-gmax") && filters.includeGmax -> learnset.add(it.pokemon.name)
                it.pokemon.name.contains("-mega") && filters.includeMega -> learnset.add(it.pokemon.name)
                filters.includeNFE -> learnset.add(it.pokemon.name)
                else -> {
                    val species = client.makeRequest<PokemonResponse>(it.pokemon.url).species.name
                }
            }
        }
        return learnset.toList()
    }
}
