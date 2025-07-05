package org.gabrielross.client.model

import kotlinx.serialization.Serializable
import org.gabrielross.constants.Language

// Contains effect data for a move or ability
@Serializable
data class EffectEntry(
    val effect: String,
    val language: ShortEntry<Language>,
    val short_effect: String
)
