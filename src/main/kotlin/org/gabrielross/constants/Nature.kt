package org.gabrielross.constants

enum class NatureModifier(val value: Double) {
    Increased(1.1),
    Decreased(.9),
    Neutral(1.0);

    companion object {
        fun lookup(nature: Nature, stat: Stat): NatureModifier {
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
    }
}

enum class Nature {
    hardy,
    lonely,
    brave,
    adamant,
    naughty,
    bold,
    docile,
    relaxed,
    impish,
    lax,
    timid,
    hasty,
    serious,
    jolly,
    naive,
    modest,
    mild,
    quiet,
    bashful,
    rash,
    calm,
    gentle,
    sassy,
    careful,
    quirky
}
