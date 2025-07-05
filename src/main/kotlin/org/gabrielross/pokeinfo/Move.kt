package org.gabrielross.pokeinfo

import org.gabrielross.client.Client
import org.gabrielross.client.model.MoveResponse
import org.gabrielross.constants.DamageClass
import org.gabrielross.constants.Language
import org.gabrielross.constants.Type
import org.gabrielross.model.Move as MoveModel

class Move(val client: Client) {

    fun get(identifier: String): org.gabrielross.model.Move {
        return MoveModel.fromResponseData(client.getMove(identifier))
    }
}