package org.gabrielross.pokeinfo

// Helper methods for standardizing argument inputs

fun cleanPotentialListInput(inp: String): String {
    return inp.filterNot{c -> c == '[' || c == ']'}
}

fun cleanNameInput(inp: String): String {
    return inp.trim().replace("_", "-").replace(" ", "-")
}