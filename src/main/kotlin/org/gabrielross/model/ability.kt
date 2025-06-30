package org.gabrielross.model

import org.gabrielross.client.response.AbilityResponse
import org.gabrielross.constants.Language


data class AbilityData(
    val id: Int,
    val name: String,
    val shortEffect: String,
    val longEffect: String
)

class Ability(
    val id: Int,
    val name: String,
    val shortEffect: String,
    val longEffect: String
) {
    companion object {
        fun FromResponse(data: AbilityResponse): Ability {
            var longEffect = ""
            var shortEffect = ""
            data.effect_entries.forEach { effectEntry ->
                if (effectEntry.language.name == Language.en) {
                    longEffect = effectEntry.effect
                    shortEffect = effectEntry.short_effect
                }
            }
            return Ability(
                id = data.id,
                name = data.name,
                longEffect = longEffect,
                shortEffect = shortEffect
            )
        }
    }

    fun Data(): AbilityData {
        return AbilityData(
            id = this.id,
            name = this.name,
            longEffect = this.longEffect,
            shortEffect = this.shortEffect
        )
    }
}
