import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
// import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
// import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun welcome(): String {
    return "Welcome!"
}

//part of gson
data class UserPages(val user: String, val pageTitle: String)
data class SignIn(val username: String, val password: String, val name: String)

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
        /////
        post("/signin") {
            val request = call.receive<SignIn>()
            val user = request.username
            call.respond(user)
            // println(request)
            // call.respond(HttpStatusCode.OK)
        }
        /////
        get("/{user}/{page}") {
            try {
                val user = call.parameters["user"]
                val page = call.parameters["page"]
                val userPage = when (page) {
                    "home" -> "Welcome back, $user!"
                    "explore" -> "These books might interest you, $user!"
                    "shelf" -> "$user's Shelf"
                    "book buddies" -> "$user's Book Buddies"
                    else -> throw Exception("The page '$page' does not exist")
                }
                call.respond(userPage)

                //the following can be used to return content in gson:
                // val pageDetails = UserPages(user!!, userPage)
                // call.respond(pageDetails)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

//ktor set up
fun main() {
    embeddedServer(Netty, port = 8004, module = Application::userPage).start(wait = true)
}