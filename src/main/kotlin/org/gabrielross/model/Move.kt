package org.gabrielross.model

import org.gabrielross.client.model.MoveResponse
import org.gabrielross.constants.DamageClass
import org.gabrielross.constants.Language
import org.gabrielross.constants.Type

// Contains data for a move object
data class Move(
    val id: Int,
    val name: String,
    val priority: Int,
    val accuracy: Int?,
    val power: Int?,
    val pp: Int,
    val type: Type,
    val damageClass: DamageClass,
    val shortEffect: String,
    val longEffect: String
) {
    companion object {
        fun fromResponseData(data: MoveResponse): Move {
            var longEffect = ""
            var shortEffect = ""
            data.effect_entries.forEach { effectEntry ->
                if (effectEntry.language.name == Language.en) {
                    longEffect = effectEntry.effect
                    shortEffect = effectEntry.short_effect
                }
            }
            return Move(
                id = data.id,
                name = data.name,
                priority = data.priority,
                accuracy = data.accuracy,
                power = data.power,
                pp = data.pp,
                type = data.type.name,
                damageClass = data.damage_class.name,
                longEffect = longEffect,
                shortEffect = shortEffect
            )
        }
}
    override fun toString(): String {
        return """
            id: $id
            name: $name
            priority: $priority
            accuracy: $accuracy
            power: $power
            pp: $pp
            damageClass: $damageClass
            type: $type
            effect: $shortEffect
        """.trimIndent()
    }
}


