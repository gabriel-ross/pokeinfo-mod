package org.gabrielross.pokeinfo

import org.gabrielross.constants.Nature
import org.gabrielross.constants.NatureModifier
import org.gabrielross.constants.NatureModifier.*
import org.gabrielross.constants.Stat
import org.gabrielross.model.Stats

fun lookupStatModifier(nature: Nature, stat: Stat): NatureModifier {
    return when (nature) {
        Nature.hardy -> Neutral
        Nature.docile -> Neutral
        Nature.serious -> Neutral
        Nature.bashful -> Neutral
        Nature.quirky -> Neutral

        Nature.lonely -> when (stat) {
            Stat.Attack -> Increased
            Stat.Defense -> Decreased
            else -> Neutral
        }
        Nature.brave -> when (stat) {
            Stat.Attack -> Increased
            Stat.Speed -> Decreased
            else -> Neutral
        }
        Nature.adamant -> when (stat) {
            Stat.Attack -> Increased
            Stat.SpecialAttack -> Decreased
            else -> Neutral
        }
        Nature.naughty -> when (stat) {
            Stat.Attack -> Increased
            Stat.SpecialDefense -> Decreased
            else -> Neutral
        }

        Nature.bold -> when (stat) {
            Stat.Attack -> Decreased
            Stat.Defense -> Increased
            else -> Neutral

        }
        Nature.relaxed -> when (stat) {
            Stat.Defense -> Increased
            Stat.Speed -> Decreased
            else -> Neutral

        }
        Nature.impish -> when (stat) {
            Stat.Defense -> Increased
            Stat.SpecialAttack -> Decreased
            else -> Neutral

        }
        Nature.lax -> when (stat) {
            Stat.Defense -> Increased
            Stat.SpecialDefense -> Decreased
            else -> Neutral

        }

        Nature.timid -> when (stat) {
            Stat.Attack -> Decreased
            Stat.Speed -> Increased
            else -> Neutral

        }
        Nature.hasty -> when (stat) {
            Stat.Defense -> Decreased
            Stat.Speed -> Increased
            else -> Neutral

        }
        Nature.jolly -> when (stat) {
            Stat.SpecialAttack -> Decreased
            Stat.Speed -> Increased
            else -> Neutral

        }
        Nature.naive -> when (stat) {
            Stat.SpecialDefense -> Decreased
            Stat.Speed -> Increased
            else -> Neutral

        }

        Nature.modest -> when (stat) {
            Stat.Attack -> Decreased
            Stat.SpecialAttack -> Increased
            else -> Neutral

        }
        Nature.mild -> when (stat) {
            Stat.Defense -> Decreased
            Stat.SpecialAttack -> Increased
            else -> Neutral

        }
        Nature.quiet -> when (stat) {
            Stat.SpecialAttack -> Increased
            Stat.Speed -> Decreased
            else -> Neutral

        }
        Nature.rash -> when (stat) {
            Stat.SpecialAttack -> Increased
            Stat.SpecialDefense -> Decreased
            else -> Neutral

        }

        Nature.calm -> when (stat) {
            Stat.Attack -> Decreased
            Stat.SpecialDefense -> Increased
            else -> Neutral

        }
        Nature.gentle -> when (stat) {
            Stat.Defense -> Decreased
            Stat.SpecialDefense -> Increased
            else -> Neutral

        }
        Nature.sassy -> when (stat) {
            Stat.SpecialDefense -> Increased
            Stat.Speed -> Decreased
            else -> Neutral

        }
        Nature.careful -> when (stat) {
            Stat.SpecialAttack -> Decreased
            Stat.SpecialDefense -> Increased
            else -> Neutral

        }
    }
}

fun cleanPotentialListInput(inp: String): String {
    return inp.filterNot{c -> c == '[' || c == ']'}
}

fun cleanNameInput(inp: String): String {
    return inp.trim().replace("_", "-").replace(" ", "-")
}