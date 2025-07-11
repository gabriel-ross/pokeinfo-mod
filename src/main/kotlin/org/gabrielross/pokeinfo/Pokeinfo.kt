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

//    // Builds the command tree and registers all commands to the provided dispatcher
//    fun buildAndRegisterCommands(dispatcher: CommandDispatcher<CommandSourceStack>) {
    // Builds the command tree and returns the root command
    fun buildCommandTree(): LiteralArgumentBuilder<CommandSourceStack> {
        val rootCmd = Commands.literal("pokeinfo")

        // Append pokemon subcommands to top-level pokemon command
        val pokemonCmd = cmdGetPokemon()
        pokemonCmd.then(cmdGetPokemonAbilities())
        pokemonCmd.then(cmdPokemonCanLearnMove())
        pokemonCmd.then(cmdGetPokemonEVYield())
        pokemonCmd.then(cmdPokemonAllBreedsWith())
        pokemonCmd.then(cmdPokemonCanBreed())


        // Append top-level subcommands (pokemon, moves, abilities, search) to root command
        rootCmd.then(pokemonCmd)

        return rootCmd
        // Register base command after building command tree
//        dispatcher.register(baseCmd)
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
    fun printToChat(ctx: CommandSourceStack, msg: String): Int {
        ctx.sendSystemMessage(Component.literal(msg))
        return 1
    }

    fun natureDoes(identifier: String): String {
        val nature = client.getNature(identifier)
        return "$identifier: +${nature.increased_stat.name} -${nature.decreased_stat.name}"
    }

}
