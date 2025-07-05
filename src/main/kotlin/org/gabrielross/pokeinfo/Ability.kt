package org.gabrielross.pokeinfo

import org.gabrielross.client.Client
import org.gabrielross.model.Ability as AbilityModel

class Ability(val client: Client) {
    fun get(identifier: String): AbilityModel {
        return AbilityModel.fromResponseData(client.getAbility(identifier))
    }

}