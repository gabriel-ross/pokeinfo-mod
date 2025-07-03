import okhttp3.OkHttpClient
import org.gabrielross.api.Pokeinfo
import org.gabrielross.client.Client
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PokeinfoTest {
    fun setup(): Pokeinfo {
        val baseHTTPClient = OkHttpClient()
        val baseUrl = "https://pokeapi.co/api/v2"
        return Pokeinfo(Client(baseUrl, baseHTTPClient))
    }
    @Test
    fun testGetMove() {
        val baseHTTPClient = OkHttpClient()
        val baseUrl = "https://pokeapi.co/api/v2"
        val cl = Client(baseUrl, baseHTTPClient)
        val pokeinfo = Pokeinfo(Client(baseUrl, baseHTTPClient))
        println(pokeinfo.getMove("headbutt").Data().toString())
//        println(pokeinfo.getMove("bullet-punch").Data().toString())
//        assertEquals(1,2)
    }

    @Test
    fun testCompoundLearnset() {
        val api = setup()
        println(api.getMoveLearnset("bullet-punch,swords-dance"))
    }

    @Test
    fun testPokemonCanLearnMove() {
        val api = setup()
        val learnsByLevelUpAndMachine = api.pokemonLearnsMove("scizor", "iron-head")
        assertEquals(true, learnsByLevelUpAndMachine.canLearnMove)
        assertEquals(36, learnsByLevelUpAndMachine.levelLearnedAt)
        assertEquals(true, learnsByLevelUpAndMachine.learnsByLevelUp)
        assertEquals(true, learnsByLevelUpAndMachine.learnsByMachine)

        val cannotLearnMove = api.pokemonLearnsMove("scizor", "flamethrower")
        assertEquals(false, cannotLearnMove.canLearnMove)

        val learnsByEvolution = api.pokemonLearnsMove("scizor", "bullet-punch")
        assertEquals(true, learnsByEvolution.canLearnMove)
        assertEquals(0, learnsByEvolution.levelLearnedAt)
        assertEquals(true, learnsByEvolution.learnsByLevelUp)
    }
}