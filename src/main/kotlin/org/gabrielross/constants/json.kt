package org.gabrielross.constants

import kotlinx.serialization.json.Json

public val UnmarshalStrategy = Json {
    ignoreUnknownKeys = true
    decodeEnumsCaseInsensitive = true
}