package org.gabrielross.pokeinfo

import okhttp3.OkHttpClient
import org.gabrielross.client.Client
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertContains
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PokemonTest {
    fun setup(): Pokemon {
        val baseHTTPClient = OkHttpClient()
        val baseUrl = "https://pokeapi.co/api/v2"
        return Pokemon(Client(baseHTTPClient, baseUrl))
    }

    @Test
    fun testCanLearnMove() {
        // todo
    }

    @Test
    fun testPokemonCanLearnMove() {
        val app = setup()

        val learnsByLevelUpAndMachine = app.learnsMove("scizor", "iron-head")
        assertEquals(true, learnsByLevelUpAndMachine.canLearnMove)
        assertEquals(36, learnsByLevelUpAndMachine.levelLearnedAt)
        assertEquals(true, learnsByLevelUpAndMachine.learnsByLevelUp)
        assertEquals(true, learnsByLevelUpAndMachine.learnsByMachine)

        val cannotLearnMove = app.learnsMove("scizor", "flamethrower")
        assertEquals(false, cannotLearnMove.canLearnMove)

        val learnsByEvolution = app.learnsMove("scizor", "bullet-punch")
        assertEquals(true, learnsByEvolution.canLearnMove)
        assertEquals(0, learnsByEvolution.levelLearnedAt)
        assertEquals(true, learnsByEvolution.learnsByLevelUp)
    }

    @Test
    fun testIsFullyEvolved() {
        val app = setup()
        assertFalse(app.isFullyEvolved("scyther"))
        assertTrue(app.isFullyEvolved("scizor"))
    }

    @Test
    fun testAllBreedsWith() {
        val cl = Client( OkHttpClient(), "https://pokeapi.co/api/v2")
        val app = Pokemon(cl)

        var expectedPokemon = mutableSetOf<String>()
        cl.getEggGroup("ground").pokemon_species.forEach { it ->
            expectedPokemon.add(it.name)
        }
        cl.getEggGroup("water2").pokemon_species.forEach { it ->
            expectedPokemon.add(it.name)
        }

        val resp = app.allBreedsWith("finizen")
        for (i in 1..10) {
            val pk = expectedPokemon.random()
            assertContains(resp, pk)
        }
    }

    @Test
    fun testCanBreed() {
        val app = setup()
        assertFalse(app.canBreed("riolu", "lucario")) // no-eggs should return false
        assertFalse(app.canBreed("scizor", "torchic")) // do not share an egg group
        assertTrue(app.canBreed("lucario", "torchic")) // do share an egg group
    }
}