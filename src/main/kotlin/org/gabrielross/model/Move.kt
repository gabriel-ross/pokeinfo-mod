package org.gabrielross.model

import org.gabrielross.client.response.MoveResponse
import org.gabrielross.constants.DamageClass
import org.gabrielross.constants.Language
import org.gabrielross.constants.Type
import kotlin.Int

// Contains data for a move object
data class MoveData(
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
    override fun toString(): String {
        return "id: ${this.id}\nname: ${this.name}\npriority: ${this.priority}\naccuracy: ${this.accuracy}\npower: ${this.power}\npp: ${this.pp}\ndamageClass: ${this.damageClass}\ntype: ${this.type}\neffect: ${this.shortEffect}\n"
    }
}

class Move(
    val id: Int,
    val name: String,
    val priority: Int,
    val accuracy: Int?,
    val power: Int?,
    val pp: Int,
    val type: Type,
    val damageClass: DamageClass,
    val longEffect: String,
    val shortEffect: String
) {
    companion object {
        fun fromResponse(data: MoveResponse): Move {
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
    fun Data(): MoveData {
        return MoveData(
            id = this.id,
            name = this.name,
            priority = this.priority,
            accuracy = this.accuracy,
            power = this.power,
            pp = this.pp,
            type = this.type,
            damageClass = this.damageClass,
            longEffect = this.longEffect,
            shortEffect = this.shortEffect
        )
    }
}
