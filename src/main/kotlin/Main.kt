import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun welcome(): String {
    return "Welcome!"
}

//ktor set up
fun main() {
    embeddedServer(Netty, 8004) {
        routing {
            get("/") {
                call.respondText("Welcome!  Please sign in.")
            }
            get("/home") {
                val user = "Megan"
                call.respondText("Welcome back, $user!")
            }
        }
    }.start(wait = true)
}