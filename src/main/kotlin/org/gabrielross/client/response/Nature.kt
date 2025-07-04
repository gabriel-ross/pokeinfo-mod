package org.gabrielross.client.response

import kotlinx.serialization.Serializable

@Serializable
data class Nature(
    val decreased_stat: ShortEntry<>
)
