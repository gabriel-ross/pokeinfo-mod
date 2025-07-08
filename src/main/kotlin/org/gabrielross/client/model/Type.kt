package org.gabrielross.client.model

import kotlinx.serialization.Serializable
import org.gabrielross.constants.Type

@Serializable
data class Type(val name: Type, val url: String)

@Serializable
data class TypeResponse(
    val id: Int,
    val name: String,
    val moves: List<ShortEntry<String>>,
    val pokemon: List<TypeEntry>
)

@Serializable
data class TypeEntry(val pokemon: ShortEntry<String>, val slot: Int)