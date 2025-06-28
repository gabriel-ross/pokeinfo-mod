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
//
//        val req = Request.Builder()
//            .url("https://pokeapi.co/api/v2/pokemon/scizor")
//            .build()
//
//        val resp = baseHTTPClient.newCall(req).execute()
//        if (!resp.isSuccessful) throw IOException("error code: ${resp.code}")
//        println(resp.body!!.string())
//        println(UnmarshalStrategy.decodeFromString<pokemonresponse>(resp.body!!.string()))

        val pokeinfo = Pokeinfo(Client(baseUrl, baseHTTPClient))
//        println(pokeinfo.getPokemon("scizor").Data().toString())

        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            BrigExampleCommand.registerPokeInfo(dispatcher, pokeinfo)
        }
        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            BrigExampleCommand.register(dispatcher)
        }
        logger.info("Hello Fabric world!")
    }
}