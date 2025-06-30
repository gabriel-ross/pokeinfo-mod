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
}