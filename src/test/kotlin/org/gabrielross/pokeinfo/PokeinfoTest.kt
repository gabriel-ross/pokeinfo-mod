package org.gabrielross.pokeinfo

import io.github.cdimascio.dotenv.Dotenv
import okhttp3.OkHttpClient
import org.gabrielross.client.Client
import org.gabrielross.client.model.EvolutionChainResponse
import org.gabrielross.constants.NatureModifier
import org.gabrielross.model.Stats
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PokeinfoTest {

    @Test
    fun demo() {
        val dotenv = Dotenv.load()
        println(dotenv.get("POKEMON_VERSION"))
        println(dotenv["POKEMON_VERSION"])
    }

//    fun setup(): Pokeinfo {
//        val baseHTTPClient = OkHttpClient()
//        val baseUrl = "https://pokeapi.co/api/v2"
//        return Pokeinfo(Client(baseHTTPClient, baseUrl))
//    }
//
//    @Test
//    fun testStatCalculator() {
//        val app = setup()
//        val expected = Stats(289, 278, 193, 135, 171, 171)
//        val base = Stats(108, 130, 95, 80, 85, 102)
//        val ivs = Stats(24, 12, 30, 16, 23, 5)
//        val evs = Stats(74, 190, 91, 48, 84, 23)
//        val level = 78
//        assertEquals(expected.hp, app.calculateHP(base.hp, ivs.hp, evs.hp, level))
//        assertEquals(expected.atk, app.calculateStat(base.atk, ivs.atk, evs.atk, level, NatureModifier.Increased))
//        assertEquals(expected.spa, app.calculateStat(base.spa, ivs.spa, evs.spa, level, NatureModifier.Decreased))
//        assertEquals(expected.spe, app.calculateStat(base.spe, ivs.spe, evs.spe, level, NatureModifier.Neutral))
//    }
//
//    @Test
//    fun testGetEvolutionChain() {
//        val baseHTTPClient = OkHttpClient()
//        val baseUrl = "https://pokeapi.co/api/v2"
//        val cl = Client(baseUrl, baseHTTPClient)
////        println(cl.getEvolutionChain(2))
//        println(cl.makeRequest<EvolutionChainResponse>("https://pokeapi.co/api/v2/evolution-chain/2/"))
//    }



}