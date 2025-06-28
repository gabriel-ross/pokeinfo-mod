package org.gabrielross.constants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Language {
    @SerialName("en")
    en,
    @SerialName("de")
    de
}