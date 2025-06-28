package org.gabrielross.client

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.gabrielross.client.response.*
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
    val moveEndpoint = "move"
    val abilityEndpoint = "ability"
    val typeEndpoint = "type"
    val growthRateEndpoint = "growth-rate"
    val eggGroupEndpoint = "egg-group"

    fun getPokemon(id: Int): PokemonResponse {
        return UnmarshalStrategy.decodeFromString<PokemonResponse>(
            this.makePokeAPIRequest("${this.pokemonEndpoint}/$id").string()
        )
    }

    fun getPokemon(name: String): PokemonResponse {
        return UnmarshalStrategy.decodeFromString<PokemonResponse>(
            this.makePokeAPIRequest("${this.pokemonEndpoint}/$name").string()
        )
    }

    fun getMove(id: Int): MoveResponse {
        return UnmarshalStrategy.decodeFromString<MoveResponse>(
            this.makePokeAPIRequest("${this.moveEndpoint}/$id").string()
        )
    }

    fun getMove(name: String): MoveResponse {
        return UnmarshalStrategy.decodeFromString<MoveResponse>(
            this.makePokeAPIRequest("${this.moveEndpoint}/$name").string()
        )
    }

    fun getAbility(id: Int): AbilityResponse {
        return UnmarshalStrategy.decodeFromString<AbilityResponse>(
            this.makePokeAPIRequest("${this.abilityEndpoint}/$id").string()
        )
    }

    fun getAbility(name: String): AbilityResponse {
        return UnmarshalStrategy.decodeFromString<AbilityResponse>(
            this.makePokeAPIRequest("${this.abilityEndpoint}/$name").string()
        )
    }

    fun getType(type: Type): TypeResponse {
        return UnmarshalStrategy.decodeFromString<TypeResponse>(
            this.makePokeAPIRequest("${this.baseUrl}/$type").string()
        )
    }

    fun getEggGroup(name: String): EggGroupResponse {
        return UnmarshalStrategy.decodeFromString<EggGroupResponse>(
            this.makePokeAPIRequest("${this.eggGroupEndpoint}/$name").string()
        )
    }

    fun getEggGroup(id: Int): EggGroupResponse {
        return UnmarshalStrategy.decodeFromString<EggGroupResponse>(
            this.makePokeAPIRequest("${this.eggGroupEndpoint}/$id").string()
        )
    }

    fun getGrowthRate(name: String): GrowthRateResponse {
        return UnmarshalStrategy.decodeFromString<GrowthRateResponse>(
            this.makePokeAPIRequest("${this.growthRateEndpoint}/$name").string()
        )
    }

    fun getGrowthRate(id: Int): GrowthRateResponse {
        return UnmarshalStrategy.decodeFromString<GrowthRateResponse>(
            this.makePokeAPIRequest("${this.growthRateEndpoint}/$id").string()
        )
    }

    private fun makePokeAPIRequest(endpoint: String): ResponseBody {
        val req = Request.Builder()
            .url("${this.baseUrl}/$endpoint")
            .build()

        val resp = this.httpClient.newCall(req).execute()
        if (!resp.isSuccessful) throw IOException("enter custom exception msg")

        return resp.body!!
    }
}