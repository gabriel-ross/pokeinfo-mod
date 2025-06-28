package org.gabrielross

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import org.gabrielross.api.Pokeinfo

class PokemonCommand {
    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSourceStack>, pokeInfo: Pokeinfo) {
            dispatcher.register(
                Commands.literal("pokeinfo")
                    .then(
                        Commands.argument("identifier", StringArgumentType.greedyString())
                            .executes { ctx ->
                                getPokemon(
                                    ctx.source,
                                    StringArgumentType.getString(ctx, "identifier"),
                                    pokeInfo
                                )
                            }
                    )
            )
        }

        fun getPokemon(source: CommandSourceStack, identifier: String, pokeinfo: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(pokeinfo.getPokemon(identifier).Data().toString()))
            return 1
        }
    }
}

class MoveCommand {
    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSourceStack>, pokeinfo: Pokeinfo) {
            dispatcher.register(
                Commands.literal("move")
                    .then(
                        Commands.argument("identifier", StringArgumentType.greedyString())
                            .executes { ctx ->
                                getMoveInfo(ctx.source, StringArgumentType.getString(ctx, "identifier"), pokeinfo)
                            }
                    )
            )
        }

        fun getMoveInfo(source: CommandSourceStack, identifier: String, pokeinfo: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(pokeinfo.getMove(identifier).Data().toString()))
            return 1
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
    }
}