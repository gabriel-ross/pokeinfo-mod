package org.gabrielross

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import okhttp3.OkHttpClient
import org.gabrielross.pokeinfo.Pokeinfo
import org.gabrielross.client.Client
import org.slf4j.LoggerFactory

object PokeinfoMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("pokeinfo")


    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        val baseHTTPClient = OkHttpClient()
        val baseUrl = "https://pokeapi.co/api/v2"

        val pokeinfo = Pokeinfo(Client(baseUrl, baseHTTPClient))

        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            pokeinfo.registerAllCommands(dispatcher)
        }
//
//        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
//            MoveCommand.register(dispatcher, pokeinfo)
//        }
//
//        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
//            AbilityCommand.register(dispatcher, pokeinfo)
//        }
//
//        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
//            NatureCommand.register(dispatcher, pokeinfo)
//        }
//
//        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
//            BaseCommand.register(dispatcher)
//        }


        logger.info("Pokeinfo is running")
    }
}