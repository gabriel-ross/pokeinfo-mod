package org.gabrielross

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.greedyString
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import org.gabrielross.api.Pokeinfo


class PokemonCommand {
    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSourceStack>, api: Pokeinfo) {
            // Defines base pokemon command
            val baseCmd = Commands.literal("pokemon").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                pokemon(ctx.source, getString(ctx, "identifier"), api)
            }).executes { ctx -> Common.noArguments(ctx.source, "/pokemon") }

            // Defines subcommands
            val abilities = Commands.literal("abilities").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                abilities(ctx.source, getString(ctx, "identifier"), api)
            })
            val evYield = Commands.literal("evyield").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                evYield(ctx.source, getString(ctx, "identifier"), api)
            })

            baseCmd.then(abilities)
            baseCmd.then(evYield)
            dispatcher.register(baseCmd)
        }

        fun pokemon(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(api.getPokemon(identifier).Data().toString()))
            return 1
        }

        fun abilities(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(api.getPokemon(identifier).Data().toString()))
            return 1
        }

        fun evYield(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(api.getPokemon(identifier).Data().evYield.toString()))
            return 1
        }
    }
}

class AbilityCommand {
    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSourceStack>, api: Pokeinfo) {
            val baseCmd = Commands.literal("ability").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                abilityInfo(ctx.source, getString(ctx, "identifier"), api)
            }).executes { ctx -> Common.noArguments(ctx.source, "/ability") }

            val effect = Commands.literal("effect").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                abilityEffect(ctx.source, getString(ctx, "identifier"), api)
            })
            val learnset = Commands.literal("learnset").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                abilityLearnset(ctx.source, getString(ctx, "identifier"), api)
            })

            baseCmd.then(effect)
            baseCmd.then(learnset)
            dispatcher.register(baseCmd)
        }

        fun abilityInfo(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(api.getAbility(identifier).Data().toString()))
            return 1
        }

        fun abilityEffect(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(api.getAbility(identifier).Data().shortEffect.toString()))
            return 1
        }

        fun abilityLearnset(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(api.getAbilityLearnset(identifier).toString()))
            return 1
        }
    }
}


class MoveCommand {
    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSourceStack>, api: Pokeinfo) {
            val baseCmd = Commands.literal("moves").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                moveInfo(ctx.source, getString(ctx, "identifier"), api)
            }).executes { ctx -> Common.noArguments(ctx.source, "/move") }

            val effect = Commands.literal("effect").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                moveEffect(ctx.source, getString(ctx, "identifier"), api)
            })
            val learnset = Commands.literal("learnset").then(Commands.argument("identifier", greedyString()).executes { ctx ->
                learnset(ctx.source, getString(ctx, "identifier"), api)
            })

            baseCmd.then(effect)
            baseCmd.then(learnset)
            dispatcher.register(baseCmd)
        }

        fun moveInfo(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(api.getMove(identifier).Data().toString()))
            return 1
        }

        fun moveEffect(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
            source.sendSystemMessage(Component.literal(api.getMove(identifier).Data().shortEffect.toString()))
            return 1
        }

        fun learnset(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
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

class HelpCommand {
    companion object {
        fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
            val rootCmd = Commands.literal("foo").then(Commands.argument("arg1", StringArgumentType.string()).then(Commands.argument("arg2",
                StringArgumentType.string()).executes { ctx ->
                ctx.source.sendSystemMessage(Component.literal("arg1: ${getString(ctx, "arg1")}, arg2: ${getString(ctx, "arg2")}"))
//                ctx.source.sendSystemMessage(Component.literal("help command received"))
                1
            }))
            dispatcher.register(rootCmd)
        }
    }
}