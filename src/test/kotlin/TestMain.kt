import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class TestMain : StringSpec({
    "should greet properly" {
        main() shouldBe "Welcome!"
    }
})
