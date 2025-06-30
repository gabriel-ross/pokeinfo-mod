package org.gabrielross.experience

class Calculator {
    companion object {
        fun CalculateCandies(inventory: CandyInventory = CandyInventory(999, 999, 999, 999, 999)): CandyInventory {
            //
        }
    }
}

var XL_VALUE = 30000
var L_VALUE = 10000
var M_VALUE = 3000
var S_VALUE = 800
var XS_VALUE = 100

data class CandyInventory(
    var XL: Int,
    var L: Int,
    var M: Int,
    var S: Int,
    var XS: Int
)