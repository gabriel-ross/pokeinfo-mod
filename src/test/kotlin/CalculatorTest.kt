import org.junit.jupiter.api.Test

data class foo(var a: Int)

class CalculatorTest {

    @Test
    fun testCalculator() {
        var a = foo(1)
        a.a -= 1
        println(a)
    }

}