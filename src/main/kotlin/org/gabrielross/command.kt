package org.gabrielross

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.greedyString
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import org.gabrielross.PokemonCommand.Companion.getAbilities
import org.gabrielross.PokemonCommand.Companion.getEvYield
import org.gabrielross.PokemonCommand.Companion.getPokemon
import org.gabrielross.api.Pokeinfo


class PokemonCommand {
    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSourceStack>, api: Pokeinfo) {
            // Defines base pokemon command
            val baseCmd = Commands.literal("pokemon").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                getPokemon(ctx.source, getString(ctx, "identifier"), api)
            }).executes { ctx -> Common.noArguments(ctx.source, "/pokemon") }

            // Defines subcommands
            val abilities = Commands.literal("abilities").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                getAbilities(ctx.source, getString(ctx, "identifier"), api)
            })
            val evYield = Commands.literal("evyield").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                getEvYield(ctx.source, getString(ctx, "identifier"), api)
            })

            baseCmd.then(abilities)
            baseCmd.then(evYield)
            dispatcher.register(baseCmd)
        }

        fun getPokemon(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(api.getPokemon(identifier).Data().toString()))
            return 1
        }

        fun getAbilities(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(api.getPokemon(identifier).Data().toString()))
            return 1
        }

        fun getEvYield(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(api.getPokemon(identifier).Data().evYield.toString()))
            return 1
        }
    }
}


class MoveCommand {
    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSourceStack>, api: Pokeinfo) {
            val baseCmd = Commands.literal("moves").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                getMoveInfo(ctx.source, getString(ctx, "identifier"), api)
            }).executes { ctx -> Common.noArguments(ctx.source, "/move") }

            val effect = Commands.literal("effect").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                getMoveEffect(ctx.source, getString(ctx, "identifier"), api)
            })
            val learnset = Commands.literal("learnset").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                getLearnset(ctx.source, getString(ctx, "identifier"), api)
            })

            baseCmd.then(effect)
            baseCmd.then(learnset)
            dispatcher.register(baseCmd)
        }

        fun getMoveInfo(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(api.getMove(identifier).Data().toString()))
            return 1
        }

        fun getMoveEffect(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(api.getMove(identifier).Data().shortEffect.toString()))
            return 1
        }

        fun getLearnset(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(api.getMoveLearnset(identifier).toString()))
            return 1
        }
    }
}

class Common {
    companion object {
        fun noArguments(source: CommandSourceStack, cmdName: String): Int {
            source.sendSystemMessage(Component.literal("$cmdName called with no arguments"))
            return 1
        }
    }
}

//
//
//class PokemonCommand {
//    companion object {
//        fun register(dispatcher: CommandDispatcher<CommandSourceStack>, api: Pokeinfo) {
//            dispatcher.register(
//                Commands.literal("pokemon")
//                    .then(
//                        Commands.argument("identifier", greedyString())
//                            .executes { ctx ->
//                                getPokemon(ctx.source, getString(ctx, "identifier"), api)
//                            }
//
//                    )
//
//                    // Registers evyield subcommand for fetching pokemon ev yield data.
//                    .then(Commands.literal("evyield").then(Commands.argument("identifier", greedyString()).executes { ctx ->
//                        getPokemonEvYield(ctx.source, getString(ctx, "identifier"), api)
//                    }))
//            )
//        }
//
//        fun getPokemon(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getPokemon(identifier).Data().toString()))
//            return 1
//        }
//
//        fun getPokemonEvYield(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getPokemon(identifier).Data().evYield.toString()))
//            return 1
//        }
//    }
//}


class BrigExampleCommand {
    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
            dispatcher.register(
                Commands.literal("broadcast")
                    .then(
                        Commands.argument("message", greedyString())
                            .executes { ctx ->
                                broadcast(ctx.source, getString(ctx, "message"))
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

class DemoCommand {
    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
            // todo: try doing root scizort
            val rootCmd = Commands.literal("root").then(Commands.argument("arg1", greedyString()).then(Commands.argument("arg2", greedyString()))).executes { ctx ->
                println("arg1: ${(getString(ctx, "arg1"))}")
                println("arg2: ${(getString(ctx, "arg2"))}")
                1
            }
//            val rootCmd = Commands.literal("root").then(Commands.argument("arg1", greedyString())).then(Commands.argument("arg2", greedyString())).executes { ctx ->
//                println("arg1: ${(getString(ctx, "arg1"))}")
//                println("arg2: ${(getString(ctx, "arg2"))}")
//                1
//            }
            val subcmd = Commands.literal("subcmd").then(Commands.argument("arg1", greedyString())).executes {
                println("hello from subcmd")
                1
            }
            rootCmd.then(subcmd)
            dispatcher.register(rootCmd)
        }
    }
}