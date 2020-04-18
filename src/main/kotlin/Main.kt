import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun welcome(): String {
    return "Welcome!"
}

//part of gson
//data class WelcomePage(val user: String)

//Adding an extension function to the class Application
fun Application.userPage() {
    //gson
    install(ContentNegotiation) {
        gson {}
    }

    //routes
    routing {
        get("/") {
            call.respondText(welcome() + " Please sign in.")
        }
        get("/{user}/home") {
            val user = call.parameters["user"]
            call.respondText("Welcome back, $user!")
        }
    }
}

//ktor set up
fun main() {
    embeddedServer(Netty, port = 8004, module = Application::userPage).start(wait = true)
}