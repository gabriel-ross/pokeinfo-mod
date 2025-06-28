import okhttp3.OkHttpClient
import org.gabrielross.api.Pokeinfo
import org.gabrielross.client.Client
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PokeinfoTest {
    @Test
    fun testGetMove() {
        val baseHTTPClient = OkHttpClient()
        val baseUrl = "https://pokeapi.co/api/v2"
        val pokeinfo = Pokeinfo(Client(baseUrl, baseHTTPClient))
        println(pokeinfo.getMove("bullet-punch").Data().toString())
        assertEquals(1,2)
    }
}