package org.gabrielross.pokeinfo

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CalculatorTest {

    @Test
    fun demo() {
        assertEquals(1, 1)
    }
//
//    @Test
//    fun testCalculator() {
//        val resp = ExperienceCalculator.calculateCandies(1250000)
//        assertEquals(41, resp.inventory.XL)
//        assertEquals(2, resp.inventory.L)
//        assertEquals(0, resp.inventory.M)
//        assertEquals(0, resp.inventory.S)
//        assertEquals(0, resp.inventory.XS)
//        assertEquals(0, resp.surplus)
//    }
//
//    @Test
//    fun notEnoughCandies() {
//        val resp = ExperienceCalculator.calculateCandies(1250000, CandyInventory(5, 5, 5, 5, 5))
//        assertEquals(5, resp.inventory.XL)
//        assertEquals(5, resp.inventory.L)
//        assertEquals(5, resp.inventory.M)
//        assertEquals(5, resp.inventory.S)
//        assertEquals(5, resp.inventory.XS)
//        assertEquals(219500, resp.xpActual)
//        assertEquals(-1030500, resp.surplus)
//    }
//
//    @Test
//    fun optimalCandyAllocation() {
//        val resp = ExperienceCalculator.calculateCandies(30200, CandyInventory(2, 0, 0, 0, 1))
//        assertEquals(2, resp.inventory.XL)
//        assertEquals(0, resp.inventory.L)
//        assertEquals(0, resp.inventory.M)
//        assertEquals(0, resp.inventory.S)
//        assertEquals(0, resp.inventory.XS)
//        assertEquals(60000-30200, resp.surplus)
//    }
}