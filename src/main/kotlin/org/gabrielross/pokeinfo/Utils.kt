package org.gabrielross.pokeinfo

import org.gabrielross.constants.Nature
import org.gabrielross.constants.NatureModifier
import org.gabrielross.constants.NatureModifier.*
import org.gabrielross.constants.Stat
import org.gabrielross.model.Stats


fun cleanPotentialListInput(inp: String): String {
    return inp.filterNot{c -> c == '[' || c == ']'}
}

fun cleanNameInput(inp: String): String {
    return inp.trim().replace("_", "-").replace(" ", "-")
}