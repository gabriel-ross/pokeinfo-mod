package org.gabrielross.client.response

import kotlinx.serialization.Serializable

// Represents any entry with the following format.
@Serializable
data class ShortEntry<T>(
    val name: T,
    val url: String
)
