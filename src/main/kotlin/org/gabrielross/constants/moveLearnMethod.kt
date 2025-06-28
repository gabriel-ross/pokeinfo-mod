package org.gabrielross.constants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MoveLearnMethod() {
    @SerialName("level-up")
    levelup,

    @SerialName("machine")
    machine,

    @SerialName("egg")
    egg,

    @SerialName("tutor")
    tutor,

    @SerialName("event")
    event,

    @SerialName("prior-evolution")
    priorevolution
}

//enum class MoveLearnMethod(name: String) {
//    levelup("level-up"),
//    machine("machine"),
//    egg("egg"),
//    tutor("tutor"),
//    event("event"),
//    priorevolution("prior-evolution")
//}