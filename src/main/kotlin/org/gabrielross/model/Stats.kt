package org.gabrielross.model

import org.gabrielross.constants.Stat

data class Stats(
    var hp: Int = 0,
    var atk: Int = 0,
    var def: Int = 0,
    var spa: Int = 0,
    var spd: Int = 0,
    var spe: Int = 0
) {
    override fun toString(): String {
        return "hp: ${this.hp}, atk: ${this.atk}, def: ${this.def}, spa: ${this.spa}, spd: ${this.spd}, spe: ${this.spe}"
    }
}
