import okhttp3.OkHttpClient
import org.gabrielross.pokeinfo.Pokeinfo
import org.gabrielross.client.Client
import org.gabrielross.client.model.EvolutionChainResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertContains

class PokeinfoTest {
    fun setup(): Pokeinfo {
        val baseHTTPClient = OkHttpClient()
        val baseUrl = "https://pokeapi.co/api/v2"
        return Pokeinfo(Client(baseUrl, baseHTTPClient))
    }

    @Test
    fun testGetEvolutionChain() {
        val baseHTTPClient = OkHttpClient()
        val baseUrl = "https://pokeapi.co/api/v2"
        val cl = Client(baseUrl, baseHTTPClient)
//        println(cl.getEvolutionChain(2))
        println(cl.makeRequest<EvolutionChainResponse>("https://pokeapi.co/api/v2/evolution-chain/2/"))
    }

    @Test
    fun testGetMove() {
        val baseHTTPClient = OkHttpClient()
        val baseUrl = "https://pokeapi.co/api/v2"
        val cl = Client(baseUrl, baseHTTPClient)
        val pokeinfo = Pokeinfo(Client(baseUrl, baseHTTPClient))
        println(pokeinfo.getMove("headbutt").Data().toString())
    }

    @Test
    fun testAbilityLearnset() {
//        val api = setup()
//        println(api.getAbilityLearnset("technician", true))
    }

    @Test
    fun testSearchByAbilityMove() {
        val api = setup()
        println(api.getAbilityAndMoveLearnset("technician", "bullet-punch,swords-dance"))
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

    @Test
    fun testIsFullyEvolved() {
        val api = setup()
        println(api.isFullyEvolved("scizor"))
        println(api.isFullyEvolved("scyther"))
    }

    @Test
    fun testBreedablePokemon() {
        val cl = Client("https://pokeapi.co/api/v2", OkHttpClient())
        val api = Pokeinfo(cl)
        var expectedPokemon = mutableSetOf<String>()
        cl.getEggGroup("ground").pokemon_species.forEach { it ->
            expectedPokemon.add(it.name)
        }
        cl.getEggGroup("water2").pokemon_species.forEach { it ->
            expectedPokemon.add(it.name)
        }

        val resp = api.getBreedablePokemon("finizen")
        for (i in 1..10) {
            val pk = expectedPokemon.random()
            assertContains(resp, pk)
        }
    }

    @Test
    fun testTwoPokemonShareEggGroup() {
        val api = setup()
        // Test method returns false on NoEggsDiscovered
        assertEquals(false, api.canBreed("riolu", "lucario"))
        // Test method returns true for two pokemon that share an egg group
        assertEquals(true, api.canBreed("lucario", "torchic"))
        // Test method returns false for two pokemon that do not share an egg group
        assertEquals(false, api.canBreed("scizor", "torchic"))
    }
}