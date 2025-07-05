package org.gabrielross.client

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.gabrielross.client.model.*
import org.gabrielross.constants.Type
import org.gabrielross.constants.UnmarshalStrategy
import java.io.IOException

open class Client constructor(
    baseUrl: String,
    httpClient: OkHttpClient
) {
    val baseUrl = baseUrl
    val httpClient = httpClient
    val pokemonEndpoint = "pokemon"
    val speciesEndpoint = "pokemon-species"
    val moveEndpoint = "move"
    val abilityEndpoint = "ability"
    val typeEndpoint = "type"
    val growthRateEndpoint = "growth-rate"
    val eggGroupEndpoint = "egg-group"
    val natureEndpoint = "nature"
    val evolutionChainEndpoint = "evolution-chain"

    fun getPokemon(identifier: String): PokemonResponse {
        return UnmarshalStrategy.decodeFromString<PokemonResponse>(
            this.makePokeAPIRequest("${this.pokemonEndpoint}/$identifier").string()
        )
    }

    fun getPokemonSpecies(identifier: String): SpeciesResponse {
        return UnmarshalStrategy.decodeFromString<SpeciesResponse>(
            this.makePokeAPIRequest("${this.speciesEndpoint}/$identifier").string()
        )
    }

    fun getMove(identifier: String): MoveResponse {
        return UnmarshalStrategy.decodeFromString<MoveResponse>(
            this.makePokeAPIRequest("${this.moveEndpoint}/$identifier").string()
        )
    }

    fun getAbility(identifier: String): AbilityResponse {
        return UnmarshalStrategy.decodeFromString<AbilityResponse>(
            this.makePokeAPIRequest("${this.abilityEndpoint}/$identifier").string()
        )
    }

    fun getType(type: Type): TypeResponse {
        return UnmarshalStrategy.decodeFromString<TypeResponse>(
            this.makePokeAPIRequest("${this.baseUrl}/${this.typeEndpoint}/$type").string()
        )
    }

    fun getEggGroup(identifier: String): EggGroupResponse {
        return UnmarshalStrategy.decodeFromString<EggGroupResponse>(
            this.makePokeAPIRequest("${this.eggGroupEndpoint}/$identifier").string()
        )
    }

    fun getGrowthRate(identifier: String): GrowthRateResponse {
        return UnmarshalStrategy.decodeFromString<GrowthRateResponse>(
            this.makePokeAPIRequest("${this.growthRateEndpoint}/$identifier").string()
        )
    }

    fun getNature(identifier: String): NatureResponse {
        return UnmarshalStrategy.decodeFromString<NatureResponse>(
            this.makePokeAPIRequest("${this.natureEndpoint}/$identifier").string()
        )
    }

    fun getEvolutionChain(identifier: Int): EvolutionChainResponse {
        return UnmarshalStrategy.decodeFromString<EvolutionChainResponse>(
            this.makePokeAPIRequest("${this.evolutionChainEndpoint}/$identifier").string()
        )
    }

    // Make a request given a full url. Useful for making requests using the
    // url field of other response objects without having to extract the
    // endpoint and id of the resource.
    inline fun <reified T>makeRequest(url: String): T {
        val req = Request.Builder()
            .url(url)
            .build()

        val resp = this.httpClient.newCall(req).execute()
        if (!resp.isSuccessful) throw IOException("Received invalid response code: ${resp.code} from ${req.url}")

        return UnmarshalStrategy.decodeFromString(resp.body!!.string())
    }

    private fun makePokeAPIRequest(endpoint: String): ResponseBody {
        val req = Request.Builder()
            .url("${this.baseUrl}/$endpoint")
            .build()

        val resp = this.httpClient.newCall(req).execute()
        if (!resp.isSuccessful) throw IOException("Received invalid response code: ${resp.code} from ${req.url}")

        return resp.body!!
    }
}