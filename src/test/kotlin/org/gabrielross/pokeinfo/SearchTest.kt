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
    fun testSearch() {
        val s = setup()
        val includeNFE = s.pokemon(
            Type.bug,
            "technician",
            "swords-dance",
            filters(includeNFE = true)
        )
        assertContains(includeNFE, "scyther")
        assertContains(includeNFE, "scizor")
        assertContains(includeNFE, "scizor-mega")

        val excludeNFE = s.pokemon(
            Type.bug,
            "technician",
            "swords-dance",
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
        println(s.moveLearnsetIntersect("draining-kiss,calm-mind,stored-power"))
        println(s.moveLearnsetIntersect("bulk-up,drain-punch"))
    }

    @Test
    fun testLearnsetUnion() {
        // todo
    }
}