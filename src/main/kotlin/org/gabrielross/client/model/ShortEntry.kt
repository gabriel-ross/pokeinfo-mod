package org.gabrielross.client.model

import kotlinx.serialization.Serializable

// Represents any entry with the following format.
@Serializable
data class ShortEntry<T>(
    val name: T,
    val url: String
)
