package org.gabrielross.pokeinfo

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.greedyString
import com.mojang.brigadier.arguments.StringArgumentType.string
import net.minecraft.commands.Commands.argument
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import org.gabrielross.client.Client
import org.gabrielross.client.model.EvolutionChainResponse
import org.gabrielross.client.model.EvolvesTo
import org.gabrielross.constants.EggGroup
import org.gabrielross.constants.MoveLearnMethod
import org.gabrielross.constants.NatureModifier
import org.gabrielross.model.Pokemon as PokemonModel
import org.gabrielross.model.Species
import java.io.IOException
import kotlin.text.replace

class Pokeinfo(val client: Client) {
    val pokemon: Pokemon = Pokemon(client)
    val ability: Ability = Ability(client)
    val move: Move = Move(client)
    val search: Search = Search(client)
    val calculate: Calculator = Calculator(client)

//    fun calculateCandies(pokemonIdentifier: String, startLevel: Int, targetLevel: Int, candyInventory: CandyInventory = CandyInventory.max()): CandyCalculatorResponse {
//        // Fetch pokemon growthrate from pokemon-species endpoint, then
//        // fetch growth rate level data from growth-rate endpoint.
//        val growthRateData = this.apiClient.getGrowthRate(this.apiClient.getPokemonSpecies(pokemonIdentifier).growth_rate.toString())
//        var start = growthRateData.levels[startLevel-1]
//        var target = growthRateData.levels[targetLevel-1]
//
//        // Search for level data if levels list is not ordered.
//        if (start.level != startLevel) {
//            start = growthRateData.levels.find { levelEntry -> levelEntry.level == startLevel }!!
//        }
//        if (target.level != targetLevel) {
//            target = growthRateData.levels.find { levelEntry -> levelEntry.level == targetLevel }!!
//        }
//
//        return ExperienceCalculator.calculateCandies(target.experience - start.experience, candyInventory)
//    }

    // Builds the command tree and returns the root command
    fun buildCommandTree(): LiteralArgumentBuilder<CommandSourceStack> {
        val rootCmd = Commands.literal("pokeinfo").executes { ctx ->
            printToChat(ctx.source, pokeinfoUsage)
        }


        // Build pokemon command tree
        val pokemonCmd = cmdGetPokemon()
        pokemonCmd.then(cmdGetPokemonAbilities())
        pokemonCmd.then(cmdPokemonCanLearnMove())
        pokemonCmd.then(cmdGetPokemonEVYield())
        pokemonCmd.then(cmdPokemonAllBreedsWith())
        pokemonCmd.then(cmdPokemonCanBreed())

        // Build ability command tree
        val abilityCmd = cmdGetAbility()
        abilityCmd.then(cmdGetAbilityEffect())

        // Build move command tree
        val moveCmd = cmdGetMove()
        moveCmd.then(cmdGetMoveEffect())

        // Build search command tree
        val searchCmd = cmdSearch()
        searchCmd.then(cmdSearchLearnsMoves())
        searchCmd.then(cmdSearchLearnsMovesUnion())
        searchCmd.then(cmdSearchHaveAbility())

        // Append subcommands to root command
        rootCmd.then(pokemonCmd)
        rootCmd.then(abilityCmd)
        rootCmd.then(moveCmd)
        rootCmd.then(cmdGetNature())
        rootCmd.then(cmdSearch())


        return rootCmd
    }

    fun printToChat(ctx: CommandSourceStack, msg: String): Int {
        ctx.sendSystemMessage(Component.literal(msg))
        return 1
    }

    fun cmdGetPokemon(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("pokemon")
            .then(argument("identifier", greedyString())
                .executes { ctx ->
                    printToChat(ctx.source, pokemon.get(getString(ctx, "identifier")).toString())
                })
    }
    fun cmdGetPokemonAbilities(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("abilities")
            .then(argument("identifier", greedyString())
                .executes { ctx ->
                    printToChat(ctx.source, pokemon.get(getString(ctx, "identifier")).abilities.toString())
            })
    }
    fun cmdPokemonCanLearnMove(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("learns")
            .then(argument("pokemon", string())
                    .then(argument("move", string())
                        .executes { ctx ->
                            printToChat(ctx.source, pokemon.learnsMove(getString(ctx, "pokemon"), getString(ctx, "move")).toString())
                        }
                    ))
    }
    fun cmdGetPokemonEVYield(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("evyield")
            .then(argument("identifier", greedyString())
                .executes { ctx ->
                    printToChat(ctx.source, pokemon.get(getString(ctx, "identifier")).evYield.toString())
                })
    }
    fun cmdPokemonAllBreedsWith(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("breedgroup")
            .then(argument("identifier", greedyString())
                .executes { ctx ->
                    printToChat(ctx.source, pokemon.allBreedsWith(getString(ctx, "identifier")).toString())
                })
    }
    fun cmdPokemonCanBreed(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("canbreed")
                .then(argument("identifier1", string())
                    .then(argument("identifier2", string())
                    .executes { ctx ->
                        printToChat(ctx.source, pokemon.canBreed(getString(ctx, "identifier1"), getString(ctx, "identifier2")).toString())
                    }))
    }

    fun cmdGetAbility(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("ability")
            .then(argument("identifier", greedyString())
                .executes { ctx ->
                    printToChat(ctx.source, ability.get(getString(ctx, "identifier")).toString())
                })
    }
    fun cmdGetAbilityEffect(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("effect")
            .then(argument("identifier", greedyString())
                .executes { ctx ->
                    printToChat(ctx.source, ability.get(getString(ctx, "identifier")).shortEffect)
                })
    }

    fun cmdGetMove(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("ability")
            .then(argument("identifier", greedyString())
                .executes { ctx ->
                    printToChat(ctx.source, move.get(getString(ctx, "identifier")).toString())
                })
    }
    fun cmdGetMoveEffect(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("effect")
            .then(argument("identifier", greedyString())
                .executes { ctx ->
                    printToChat(ctx.source, move.get(getString(ctx, "identifier")).shortEffect)
                })
    }

    fun cmdGetNature(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("nature")
            .then(argument("identifier", greedyString())
                .executes { ctx ->
                    printToChat(ctx.source, natureDoes(getString(ctx, "identifier")))
                })
    }

    fun cmdSearch(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("search")
            .then(argument("args", greedyString())
                .executes { ctx ->
                    printToChat(ctx.source, search.pokemon(getString(ctx, "args")).toString())
                })
            .executes { ctx ->
                printToChat(ctx.source, searchUsage)
            }
    }
    fun cmdSearchLearnsMoves(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("learns")
            .then(argument("identifiers", greedyString())
                .executes { ctx ->
                    printToChat(ctx.source, search.learnsMoves(getString(ctx, "identifiers"), intersects = true).toString())
                })
    }
    fun cmdSearchLearnsMovesUnion(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("learnsAnyOf")
            .then(argument("identifiers", greedyString())
                .executes { ctx ->
                    printToChat(ctx.source, search.learnsMoves(getString(ctx, "identifiers"), intersects = false).toString())
                })
    }
    fun cmdSearchHaveAbility(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("ability")
            .then(argument("identifier", greedyString())
                .executes { ctx ->
                    printToChat(ctx.source, search.haveAbility(getString(ctx, "identifier")).toString())
                })
    }

    fun natureDoes(identifier: String): String {
        val nature = client.getNature(identifier)
        return "$identifier: +${nature.increased_stat.name} -${nature.decreased_stat.name}"
    }
}

private val pokeinfoUsage = """
    Pokeinfo is a tool for quickly looking up pokemon data
    
    Formatting:
    * List inputs are comma-delimited with no spaces between arguments
    * Spaces within identifier names should be replaced with hyphens (ex. great tusk -> great-tusk)
    * Alternate pokemon forms (like regional forms and megas) appended to the name 
    (ex. alolan ninetales -> ninetales-alola or mega scizor -> scizor-mega)
    
    Subcommands:
    pokemon: get info on a pokemon
    ability: get info on an ability
    move: get info on an move
    search: search for pokemon by ability, learnset, or type (anything that would return a list of pokemon really)
""".trimIndent()

private val searchUsage = """
    Usage: search [ability] [move | moves] [type | types] {--includeGmax} {--includeMega} {--includeNFE}
    Example: search ability=technician moves=swords-dance,bullet-punch type=steel --includeGmax=false --includeMega=true
    
    Description:
    Used for searching for pokemon by ability, move learnset, and/or type.
    Also provides subcommands for searching by one of the aforemementioned parameters,
    however these subcommands do not permit flags.
""".trimIndent()
