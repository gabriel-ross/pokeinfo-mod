package org.gabrielross.constants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class VersionGroup() {
    @SerialName("red-blue")
    RED_BLUE,

    @SerialName("yellow")
    YELLOW,

    @SerialName("gold-silver")
    GOLD_SILVER,

    @SerialName("crystal")
    CRYSTAL,

    @SerialName("ruby-sapphire")
    RUBY_SAPPHIRE,

    @SerialName("emerald")
    EMERALD,

    @SerialName("firered-leafgreen")
    FIRE_RED_LEAF_GREEN,

    @SerialName("diamond-pearl")
    DIAMOND_PEARL,

    @SerialName("platinum")
    PLATINUM,

    @SerialName("heartgold-soulsilver")
    HEART_GOLD_SOUL_SILVER,

    @SerialName("black-white")
    BLACK_WHITE,

    @SerialName("colosseum")
    COLOSSEUM,

    @SerialName("xd")
    XD,

    @SerialName("black-2-white-2")
    BLACK_2_WHITE_2,

    @SerialName("x-y")
    X_Y,

    @SerialName("omega-ruby-alpha-sapphire")
    OMEGA_RUBY_ALPHA_SAPPHIRE,

    @SerialName("sun-moon")
    SUN_MOON,

    @SerialName("ultra-sun-ultra-moon")
    ULTRA_SUN_ULTRA_MOON,

    @SerialName("lets-go-pikachu-lets-go-eevee")
    LETS_GO_PIKACHU_LETS_GO_EEVEE,

    @SerialName("legends-arceus")
    LEGENDS_ARCEUS,

    @SerialName("brilliant-diamond-and-shining-pearl")
    BRILLIANT_DIAMOND_AND_SHINING_PEARL,

    @SerialName("sword-shield")
    SWORD_SHIELD,

    @SerialName("scarlet-violet")
    SCARLET_VIOLET,

    @SerialName("the-teal-mask")
    THE_TEAL_MASK,

    @SerialName("the-indigo-disk")
    THE_INDIGO_DISK
}

//enum class VersionGroup(name: String) {
//    redblue("red-blue"),
//    yellow("yellow"),
//    goldsilver("gold-silver"),
//    crystal("crystal"),
//    rubysapphire("ruby-sapphire"),
//    emerald("emerald"),
//    frlg("firered-leafgreene"),
//    diamondpearl("diamond-pearl"),
//    platinum("platinum"),
//    hgss("heartgold-soulsilver"),
//    bw("black-white"),
//    colosseum("colosseum"),
//    xd("xd"),
//    b2w2("black-2-white-2"),
//    xy("x-y"),
//    oras("omega-ruby-alpha-saphire"),
//    sm("sun-moon"),
//    usum("ultra-sun-ultra-moon"),
//    lgpe("lets-go-pikachu-lets-go-eevee"),
//    bdsp("brilliant-diamond-shining-pearl"),
//    swsh("sword-shield"),
//    scvi("scarlet-violet")
//}