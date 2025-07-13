package org.gabrielross.model

import org.gabrielross.client.model.AbilityResponse
import org.gabrielross.constants.Language


data class Ability(
    val id: Int,
    val name: String,
    val shortEffect: String,
    val longEffect: String
) {
    companion object {
        fun fromResponseData(resp: AbilityResponse): Ability {
            var longEffect = ""
            var shortEffect = ""
            resp.effect_entries.forEach { effectEntry ->
                if (effectEntry.language.name == Language.en) {
                    longEffect = effectEntry.effect
                    shortEffect = effectEntry.short_effect
                }
            }
            return Ability(
                id = resp.id,
                name = resp.name,
                longEffect = longEffect,
                shortEffect = shortEffect
            )
        }
    }

    override fun toString(): String {
        return """
            id: $id
            name: $name
            effect: $shortEffect
        """.trimIndent()
    }
}


