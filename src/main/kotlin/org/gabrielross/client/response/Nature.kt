package org.gabrielross.client.response

import kotlinx.serialization.Serializable
import org.gabrielross.constants.Stat

@Serializable
data class NatureResponse(
    val decreased_stat: ShortEntry<Stat>,
    val increased_stat: ShortEntry<Stat>
)
