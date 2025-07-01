import org.gabrielross.constants.GrowthRate
import org.junit.jupiter.api.Test

data class foo(var a: Int)

class CalculatorTest {

    @Test fun demo() {
        val a = GrowthRate.MediumSlow.name
        println(a)
        println(150.floorDiv(100))
    }

    @Test
    fun testCalculator() {
        var a = foo(1)
        a.a -= 1
        println(a)
    }

    @Test
    fun notEnoughCandies() {

    }

    @Test
    fun notEnoughSmallerCandies() {

    }

}