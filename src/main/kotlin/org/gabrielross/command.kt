package org.gabrielross

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import org.gabrielross.api.Pokeinfo
import org.gabrielross.api.PokeinfoAPI

class MoveCommand {
    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
            dispatcher.register(
                Commands.literal("move")
                    .then(
                        Commands.argument("identifier", StringArgumentType.greedyString())
                            .executes { ctx ->
                                //
                            }
                    )
            )
        }

        fun getMoveInfo(source: CommandSourceStack, identifier: String, pokeinfo: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(pokeinfo.get()))
        }
    }
}

class BrigExampleCommand {
    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
            dispatcher.register(
                Commands.literal("broadcast")
                    .then(
                        Commands.argument("message", StringArgumentType.greedyString())
                            .executes { ctx ->
                                broadcast(ctx.source, StringArgumentType.getString(ctx, "message"))
                            }
                    )
            )
        }

        fun broadcast(source: CommandSourceStack, message: String): Int {
            println("broadcast called")
            source.sendSystemMessage(Component.literal(message))
            return 1
        }

        fun registerPokeInfo(dispatcher: CommandDispatcher<CommandSourceStack>, pokeInfo: PokeinfoAPI) {
            dispatcher.register(
                Commands.literal("pokeinfo")
                    .then(
                        Commands.argument("identifier", StringArgumentType.greedyString())
                            .executes { ctx ->
                                getPokemonData(
                                    ctx.source,
                                    StringArgumentType.getString(ctx, "identifier"),
                                    pokeInfo
                                )
                            }
                    )
            )
        }

        fun getPokemonData(source: CommandSourceStack, identifier: String, pokeinfo: PokeinfoAPI): Int {
            source.sendSystemMessage(Component.literal(pokeinfo.getPokemon(identifier).Data().toString()))
            return 1
        }
    }
}