package org.gabrielross.constants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MoveLearnMethod() {
    @SerialName("level-up")
    LevelUp,

    @SerialName("machine")
    Machine,

    @SerialName("egg")
    Egg,

    @SerialName("tutor")
    Tutor,

    @SerialName("event")
    Event,

    @SerialName("prior-evolution")
    PriorEvolution;

    companion object {
        fun ValueOf(m: MoveLearnMethod): String {
            when (m) {
                LevelUp -> "level-up"
                Machine -> "machine"
                Egg -> "egg"
                Tutor -> "tutor"
                Event -> "event"
                PriorEvolution -> "prior-evolution"
            }
            return ""
        }
    }
}

//enum class MoveLearnMethod(name: String) {
//    levelup("level-up"),
//    machine("machine"),
//    egg("egg"),
//    tutor("tutor"),
//    event("event"),
//    priorevolution("prior-evolution")
//}