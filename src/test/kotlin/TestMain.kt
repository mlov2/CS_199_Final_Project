import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication

// class TestMain : StringSpec({
//     "should greet properly" {
//         welcome() shouldBe "Welcome!"
//     }
// })

class TestMain : StringSpec({
    "should retrieve root path properly" {
        withTestApplication(Application::userPage) {
            handleRequest(HttpMethod.Get, "/").apply {
                response.status() shouldBe HttpStatusCode.OK
                response.content shouldBe "Welcome! Please sign in."
            }
        }
    }
    "should accept post signin request" {
        withTestApplication(Application::userPage) {
            handleRequest(HttpMethod.Post, "/signin") {
                addHeader("content-type", "application/json")
                setBody("""
                    {
                        "username": "user",
                        "password": "password",
                        "name": "name"
                    }
                """.trim())
            }.also {

            }
        }
    }
    "should sign in properly" {
        withTestApplication(Application::userPage) {
            handleRequest(HttpMethod.Get, "/user/home").apply {
                response.status() shouldBe HttpStatusCode.OK
                response.content shouldBe "Welcome back, user!"
            }
        }
    }
})