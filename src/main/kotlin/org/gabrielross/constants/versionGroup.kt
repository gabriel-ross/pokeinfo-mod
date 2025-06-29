package org.gabrielross.constants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class VersionGroup() {
    @SerialName("red-blue")
    redblue,

    @SerialName("yellow")
    yellow,

    @SerialName("gold-silver")
    goldsilver,

    @SerialName("crystal")
    crystal,

    @SerialName("ruby-sapphire")
    rubysapphire,

    @SerialName("emerald")
    emerald,

    @SerialName("firered-leafgreen")
    frlg,

    @SerialName("diamond-pearl")
    diamondpearl,

    @SerialName("platinum")
    platinum,

    @SerialName("heartgold-soulsilver")
    hgss,

    @SerialName("black-white")
    bw,

    @SerialName("colosseum")
    colosseum,

    @SerialName("xd")
    xd,

    @SerialName("black-2-white-2")
    b2w2,

    @SerialName("x-y")
    xy,

    @SerialName("omega-ruby-alpha-sapphire")
    oras,

    @SerialName("sun-moon")
    sm,

    @SerialName("ultra-sun-ultra-moon")
    usum,

    @SerialName("lets-go-pikachu-lets-go-eevee")
    lgpe,

    @SerialName("legends-arceus")
    la,

    @SerialName("brilliant-diamond-and-shining-pearl")
    bdsp,

    @SerialName("sword-shield")
    swsh,

    @SerialName("scarlet-violet")
    scvi
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