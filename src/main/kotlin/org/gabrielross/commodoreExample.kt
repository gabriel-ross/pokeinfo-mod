package org.gabrielross

import com.github.stivais.commodore.Commodore
import com.github.stivais.commodore.nodes.LiteralNode
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.client.User
import net.minecraft.commands.CommandSourceStack

class Excmd {
    companion object {
        fun execCmd(dispatcher: CommandDispatcher<CommandSourceStack>) {
            val root = Commodore("root") {
                literal("hello") {
                    literal("goodbye")
                        .runs { name: String, x: Double, y: Double, z: Double ->
                            println("goodbye $name at $x $y $z")
                        }
                }
                builder
            }
            val r = LiteralNode("foo")

            root.register(dispatcher)
        }

        fun register(dispatcher: CommandDispatcher<User>) {
            //
        }
    }
}