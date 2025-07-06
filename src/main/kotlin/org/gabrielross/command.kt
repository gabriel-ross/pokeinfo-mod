package org.gabrielross

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.greedyString
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import org.gabrielross.pokeinfo.Pokeinfo
import org.gabrielross.constants.Nature


//class PokemonCommand {
//    companion object {
//        fun register(dispatcher: CommandDispatcher<CommandSourceStack>, api: Pokeinfo) {
//            // Defines base pokemon command
//            val baseCmd = Commands.literal("pokemon").then(Commands.argument("identifier", greedyString()).executes { ctx ->
//                pokemon(ctx.source, getString(ctx, "identifier"), api)
//            }).executes { ctx -> Common.noArguments(ctx.source, "/pokemon") }
//
//            // Defines subcommands
//            val abilities = Commands.literal("abilities").then(Commands.argument("identifier", greedyString()).executes { ctx ->
//                abilities(ctx.source, getString(ctx, "identifier"), api)
//            })
//            val evYield = Commands.literal("evyield").then(Commands.argument("identifier", greedyString()).executes { ctx ->
//                evYield(ctx.source, getString(ctx, "identifier"), api)
//            })
//            val catchRate = Commands.literal("catchrate").then(Commands.argument("identifier", greedyString()).executes { ctx ->
//                catchRate(ctx.source, getString(ctx, "identifier"), api)
//            })
//            val breedsWith = Commands.literal("breedswith").then(Commands.argument("identifier", greedyString()).executes { ctx ->
//                breedsWith(ctx.source, getString(ctx, "identifier"), api)
//            })
//            val canBreed = Commands.literal("canbreed")
//                .then(Commands.argument("identifier1", StringArgumentType.string())
//                    .then(Commands.argument("identifier2", StringArgumentType.string())
//                    .executes { ctx ->
//                        canBreed(ctx.source, getString(ctx, "identifier1"), getString(ctx, "identifier2"), api)
//                    }))
//            val learnsMove = Commands.literal("learnsmove")
//                .then(Commands.argument("pokemon", StringArgumentType.string())
//                    .then(Commands.argument("move", StringArgumentType.string())
//                        .executes { ctx ->
//                            learnsMove(ctx.source, getString(ctx, "pokemon"), getString(ctx, "move"), api)
//                        }
//                    ))
//            val search = Commands.literal("search")
//                .then(Commands.argument("ability", StringArgumentType.string())
//                    .then(Commands.argument("moves", greedyString())
//                    .executes { ctx ->
//                        searchByAbilityMove(ctx.source, getString(ctx, "ability"), getString(ctx, "moves"), false, api)
//                    }.then(Commands.argument("onlyFullyEvolved", BoolArgumentType.bool())
//                        .executes { ctx ->
//                            searchByAbilityMove(ctx.source, getString(ctx, "ability"), getString(ctx, "moves"), BoolArgumentType.getBool(ctx, "onlyFullyEvolved"), api)
//                        })))
//
//
//            baseCmd.then(generateHelpCommand(PokemonHelpCommand))
//            baseCmd.then(abilities)
//            baseCmd.then(evYield)
//            baseCmd.then(catchRate)
//            baseCmd.then(breedsWith)
//            baseCmd.then(canBreed)
//            baseCmd.then(learnsMove)
//            baseCmd.then(search)
//            dispatcher.register(baseCmd)
//        }
//
//        fun pokemon(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getPokemon(identifier).Data().toString()))
//            return 1
//        }
//
//        fun abilities(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getPokemon(identifier).Data().toString()))
//            return 1
//        }
//
//        fun evYield(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getPokemon(identifier).Data().evYield.toString()))
//            return 1
//        }
//
//        fun catchRate(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getPokemonSpecies(identifier).captureRate.toString()))
//            return 1
//        }
//
//        fun breedsWith(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getBreedablePokemon(identifier).joinToString(",")))
//            return 1
//        }
//
//        fun canBreed(source: CommandSourceStack, identifier1: String, identifier2: String, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.canBreed(identifier1, identifier2).toString()))
//            return 1
//        }
//
//        fun learnsMove(source: CommandSourceStack, pokemon: String, move: String, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.pokemonLearnsMove(pokemon, move).toString()))
//            return 1
//        }
//
//        fun searchByAbilityMove(source: CommandSourceStack, ability: String, moves: String, filterFullyEvolved: Boolean, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getAbilityAndMoveLearnset(ability, moves).toString()))
//            return 1
//        }
//    }
//}
//
//class AbilityCommand {
//    companion object {
//        fun register(dispatcher: CommandDispatcher<CommandSourceStack>, api: Pokeinfo) {
//            val baseCmd = Commands.literal("ability").then(Commands.argument("identifier", greedyString()).executes { ctx ->
//                abilityInfo(ctx.source, getString(ctx, "identifier"), api)
//            }).executes { ctx -> Common.noArguments(ctx.source, "/ability") }
//
//            val effect = Commands.literal("effect").then(Commands.argument("identifier", greedyString()).executes { ctx ->
//                abilityEffect(ctx.source, getString(ctx, "identifier"), api)
//            })
//            val learnset = Commands.literal("learnset").then(Commands.argument("identifier",
//                StringArgumentType.string()).executes { ctx ->
//                abilityLearnset(ctx.source, getString(ctx, "identifier"), false, api)
//            }.then(Commands.argument("onlyFullyEvolved", BoolArgumentType.bool()).executes { ctx ->
//                abilityLearnset(ctx.source, getString(ctx, "identifier"), BoolArgumentType.getBool(ctx, "onlyFullyEvolved"), api)
//            }))
//
//            baseCmd.then(generateHelpCommand(AbilityHelpCommand))
//            baseCmd.then(effect)
//            baseCmd.then(learnset)
//            dispatcher.register(baseCmd)
//        }
//
//        fun abilityInfo(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getAbility(identifier).Data().toString()))
//            return 1
//        }
//
//        fun abilityEffect(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getAbility(identifier).Data().shortEffect.toString()))
//            return 1
//        }
//
//        fun abilityLearnset(source: CommandSourceStack, identifier: String, onlyFullyEvolved: Boolean, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getAbilityLearnset(identifier, onlyFullyEvolved).toString()))
//            return 1
//        }
//    }
//}
//
//
//class MoveCommand {
//    companion object {
//        fun register(dispatcher: CommandDispatcher<CommandSourceStack>, api: Pokeinfo) {
//            val baseCmd = Commands.literal("moves").then(Commands.argument("identifier", greedyString()).executes { ctx ->
//                moveInfo(ctx.source, getString(ctx, "identifier"), api)
//            }).executes { ctx -> Common.noArguments(ctx.source, "/move") }
//
//            val effect = Commands.literal("effect").then(Commands.argument("identifier", greedyString()).executes { ctx ->
//                moveEffect(ctx.source, getString(ctx, "identifier"), api)
//            })
//            val learnsetIntersect = Commands.literal("learnset").then(Commands.argument("moves", greedyString()).executes { ctx ->
//                learnsetIntersect(ctx.source, getString(ctx, "moves"), false, api)
//            }.then(Commands.argument("onlyFullyEvolved", BoolArgumentType.bool())
//                .executes { ctx ->
//                    learnsetIntersect(ctx.source, getString(ctx, "moves"), BoolArgumentType.getBool(ctx, "onlyFullyEvolved"), api)
//                }))
//            val learnsetOr = Commands.literal("learnsetor").then(Commands.argument("moves", greedyString()).executes { ctx ->
//                learnsetOr(ctx.source, getString(ctx, "moves"), false, api)
//            }.then(Commands.argument("onlyFullyEvolved", BoolArgumentType.bool())
//                .executes { ctx ->
//                    learnsetOr(ctx.source, getString(ctx, "moves"), BoolArgumentType.getBool(ctx, "onlyFullyEvolved"), api)
//                }))
//
//            baseCmd.then(generateHelpCommand(MoveHelpCommand))
//            baseCmd.then(effect)
//            baseCmd.then(learnsetIntersect)
//            baseCmd.then(learnsetOr)
//            dispatcher.register(baseCmd)
//        }
//
//        fun moveInfo(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getMove(identifier).Data().toString()))
//            return 1
//        }
//
//        fun moveEffect(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getMove(identifier).Data().shortEffect.toString()))
//            return 1
//        }
//
//        fun learnsetIntersect(source: CommandSourceStack, identifier: String, onlyFullyEvolved: Boolean, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getMoveLearnset(identifier, onlyFullyEvolved).toString()))
//            return 1
//        }
//
//        fun learnsetOr(source: CommandSourceStack, identifier: String, onlyFullyEvolved: Boolean, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.getMoveLearnsetUnion(identifier, onlyFullyEvolved).toString()))
//            return 1
//        }
//    }
//}
//
//class NatureCommand {
//    companion object {
//        fun register (dispatcher: CommandDispatcher<CommandSourceStack>, api: Pokeinfo) {
//            val getNature = Commands.literal("nature").then(Commands.argument("nature", greedyString()).executes { ctx ->
//                getNature(ctx.source, getString(ctx, "nature"), api)
//            })
//            val listNatures = Commands.literal("list").executes { ctx ->
//                ctx.source.sendSystemMessage(Component.literal(Nature.entries.toString()))
//                1
//            }
//            getNature.then(listNatures)
//            dispatcher.register(getNature)
//        }
//
//        fun getNature(source: CommandSourceStack, identifier: String, api: Pokeinfo): Int {
//            source.sendSystemMessage(Component.literal(api.natureDoes(identifier)))
//            return 1
//        }
//    }
//}
//
//
//class BaseCommand {
//    companion object {
//        fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
//
////            val rootCmd = Commands.literal("foo").then(Commands.argument("arg1", StringArgumentType.string()).then(Commands.argument("arg2",
////                StringArgumentType.string()).executes { ctx ->
////                ctx.source.sendSystemMessage(Component.literal("arg1: ${getString(ctx, "arg1")}, arg2: ${getString(ctx, "arg2")}"))
//////                ctx.source.sendSystemMessage(Component.literal("help command received"))
////                1
////            }))
//
//            val rootCmd = Commands.literal("pokeinfo")
//            val help = Commands.literal("help")
//                .executes { ctx ->
//                    ctx.source.sendSystemMessage(Component.literal(RootHelpCommand))
//                    1
//                }
//            val format = Commands.literal("formatting")
//                .executes { ctx ->
//                    ctx.source.sendSystemMessage(Component.literal(FormattingHelpCommand))
//                    1
//                }
//
//            help.then(format)
//            rootCmd.then(help)
//            dispatcher.register(rootCmd)
//        }
//    }
//}
//
//class Common {
//    companion object {
//        fun noArguments(source: CommandSourceStack, cmdName: String): Int {
//            source.sendSystemMessage(Component.literal("$cmdName called with no arguments"))
//            return 1
//        }
//    }
//}
//
//private fun generateHelpCommand(txt: String): LiteralArgumentBuilder<CommandSourceStack> {
//    return Commands.literal("help").executes { ctx ->
//        ctx.source.sendSystemMessage(Component.literal(txt))
//        1
//    }
//}

val PokemonHelpCommand = """
    The pokemon command provides the following commands
    and functionality:
    - /pokemon {identifier}: retrieve all data for a pokemon
    - /pokemon abilities {identifier}: retrieve a given pokemon's abilities
    - /pokemon evYield {identifier}: retrieve the ev yield for a given pokemon
    - /pokemon breedsWith {identifier}: returns all pokemon that share en egg group with a given pokemon
    - /pokemon canBreed {identifier1} {identifier2}: returns whether two pokemon can breed
    - /pokemon learnsmove {pokemon} {move}: Returns the ways in which a pokemon can learn a move
        * Limitation: Does not currently support egg moves for evolved pokemon
    - /pokemon search {ability} {moves}: Returns all pokemon (including megas) that posess a given ability and the listed moves. 
        * moves can either be a single move or a comma-delimited list of moves
""".trimIndent()

val AbilityHelpCommand = """
    The ability command provides the following commands
    and functionality:
    - /ability {identifier}: retrieve all data for am ability
    - /ability effect {identifier}: retrieve the effect of an ability in short-form
    - /ability learnset {identifier}: Retrieve the names of pokemon with this ability (including megas)
""".trimIndent()

val MoveHelpCommand = """
    The moves command provides the following commands
    and functionality:
    - /moves {identifier}: Retrieve all data for a move
    - /moves effect {identifier}: Retrieve the effect for a given move
    - /moves learnset {identifiers}: Retrieve the names of pokemon that learn all of the given move(s)
        * accepts one move or a comma-delimited list of moves
    - /moves learnsetor {identifiers}: Retrieves the pokemon that learn any of the given moves
        * accepts one move or a comma-delimited list of moves
""".trimIndent()

val RootHelpCommand = """
    Pokeinfo is a tool for quickly looking up pokemon data. 
    It supports the following usage and functionality:
    - /pokemon: access pokemon information
    - /abilities: access ability information
    - /moves: access move information
    
    More detailed documentation for each command and its
    subcommands can be found using /{command} help
    """.trimIndent()

val FormattingHelpCommand = """
    How inputs should be formatted:
    - All lists should be comma-delimited
    - Spaces in names should be replaced with hyphens (ex. great tusk -> great-tusk). 
        * The tool can generally handle spaces within names or spaces replaced with underscores, but doing so may occasionally cause issues.
""".trimIndent()
