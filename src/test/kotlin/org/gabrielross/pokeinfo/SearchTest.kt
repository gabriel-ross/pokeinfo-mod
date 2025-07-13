package org.gabrielross.pokeinfo

import okhttp3.OkHttpClient
import org.gabrielross.client.Client
import org.gabrielross.constants.Type
import org.junit.jupiter.api.Test
import kotlin.test.assertContains
import kotlin.test.assertNull

class SearchTest {
    fun setup(): Search {
        val cl = Client(OkHttpClient(), "https://pokeapi.co/api/v2")
        return Search(cl)
    }

    @Test
    fun foo() {
//        val s = "moves=drain-punch,bullet-seed type=grass,fighting --includeNFE=true"
//        val extracted = s.lowercase().split(" ").map { it.split("=") }.associate { it.first() to it.last() }
//        println(extracted["includegmax"].toBoolean())
//        println(extracted["--includenfe"].toBoolean())
//        println(extracted["ability"])
//        if (extracted.contains("ability")) {
//            println(extracted["ability"])
//        }
//        if (extracted.contains("moves")) {
//            println(extracted["moves"])
//        }
        val s = listOf("hello", "goodbye", "foo")
        println(s.take(2))
        val a = listOf("hello")
        println(a.take(2))
    }

    @Test
    fun testSearch() {
        val s = setup()
        val includeNFE = s.pokemon(
            "technician",
            "swords-dance",
            listOf(Type.bug),
            filters(includeNFE = true)
        )
        assertContains(includeNFE, "scyther")
        assertContains(includeNFE, "scizor")
        assertContains(includeNFE, "scizor-mega")

        val excludeNFE = s.pokemon(
            "technician",
            "swords-dance",
            listOf(Type.bug),
            filters(includeNFE = false)
        )
        assertNull(excludeNFE.find { it -> it == "scyther" })
        assertContains(excludeNFE, "scizor")
        assertContains(excludeNFE, "scizor-mega")
    }

    @Test
    fun testHaveAbility() {
        //
    }

    @Test
    fun testLearnsetIntersection() {
        val s = setup()
        println(s.learnsMoves("draining-kiss,calm-mind,stored-power", intersects = true))
        println(s.learnsMoves("bulk-up,drain-punch", intersects = true))
    }

    @Test
    fun testLearnsetUnion() {
        // todo
    }
}