package org.gabrielross

import kotlinx.serialization.Serializable
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import okhttp3.OkHttpClient
import org.gabrielross.api.Pokeinfo
import org.gabrielross.client.Client
import org.slf4j.LoggerFactory

@Serializable
data class pokemonresponse(val name: String, val id: Int)

object PokeInfo : ModInitializer {
    private val logger = LoggerFactory.getLogger("pokeinfo")


    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        val baseHTTPClient = OkHttpClient()
        val baseUrl = "https://pokeapi.co/api/v2"

        val pokeinfo = Pokeinfo(Client(baseUrl, baseHTTPClient))

        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            PokemonCommand.register(dispatcher, pokeinfo)
        }

        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            MoveCommand.register(dispatcher, pokeinfo)
        }
//
//        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
//            BrigExampleCommand.register(dispatcher)
//        }

        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            DemoCommand.register(dispatcher)
        }


        logger.info("Hello Fabric world!")
    }
}