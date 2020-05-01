// import freemarker.cache.ClassTemplateLoader
import com.github.mustachejava.DefaultMustacheFactory
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
// import io.ktor.freemarker.FreeMarker
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
// import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
// import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.mustache.Mustache
import io.ktor.mustache.MustacheContent

fun welcome(): String {
    return "Welcome!"
}

//part of gson
data class UserPages(val user: String, val pageTitle: String)
data class SignIn(val username: String, val password: String, val name: String)

//book info
data class BookInfo(val title: String, val author: String)

//Adding an extension function to the class Application
fun Application.userPage() {
    //gson
    install(ContentNegotiation) {
        gson {}
    }

    // //FreeMarker
    // install(FreeMarker) {
    //     templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    // }

    // Mustache
    install(Mustache) {
        mustacheFactory = DefaultMustacheFactory("templates")
    }

    //routes
    routing {
        get("/") {
         //   val phrase = "my CS 199 Final Project"
         //   call.respond(FreeMarkerContent("Welcome.ftl", phrase))
            call.respondText(
                welcome() +
                    "\n\n\nPlease sign in to continue." +
                    "\nUsername: <TEXT FIELD TO TYPE USERNAME GOES HERE>" +
                    "\nPassword: <TEXT FIELD TO TYPE PASSWORD GOES HERE>" +
                    "\n<SIGN IN BUTTON GOES HERE>" +
                    "\n\nDon't have an account?  Click here to sign up! <SIGN UP BUTTON GOES HERE>"
            )
        }
        /////
        // post("/signin") {
        //     val request = call.receive<SignIn>()
        //     val user = request.username
        //     call.respond(user)
        //     println(request)
        //     // call.respond(HttpStatusCode.OK)
        // }
        /////
        get("/{user}/{page}") {
            try {
                val user = call.parameters["user"]
                val page = call.parameters["page"]
                val userPage = when (page) {
                    "home" -> "Welcome back, $user!"
                    "explore" -> "These books might interest you, $user!"
                    "library" ->
                        "$user's Library" +
                            "\n\n\nCurrently Reading" +
                            "\n${shelves()}" +
                            "\n\nWant to Read" +
                            "\n${shelves()}" +
                            "\n\nBooks Recommended by Your Book Buddies" +
                            "\n${shelves()}" +
                            "\n\nFinished Reading" +
                            "\n${shelves()}" +
                            "\n\n$user's Favorites <3" +
                            "\n${shelves()}" +
                            "\n\nAdd a personalized shelf to your library! <BUTTON TO ADD A SHELF GOES HERE>"
                    "book buddies" ->
                        "$user's Book Buddies" +
                            "\nYou currently don't have any book buddies, but these users have read the same books as you--maybe they'll be your next book buddy! :)"
                    else -> throw Exception("The page '$page' does not exist")
                }
                // call.respond(userPage)

                //the following can be used to return content in gson:
                //val pageDetails = UserPages(user!!, userPage)

                //For Mustache
                val pageDetails = UserPages("Megan", "home")
                call.respond(MustacheContent("TestTemplate.html", mapOf("pageDetails" to pageDetails)))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

//ktor set up for server
fun main() {
    embeddedServer(Netty, port = 8014, module = Application::userPage).start(wait = true)
}

//lists for bookshelves
val currentlyReadingShelf = mutableListOf("Moment of Truth", "Life As We Knew It")
val wantToReadShelf = mutableListOf("Five Feet Apart")
val recommendedShelf = mutableListOf("The Rest of The Story")
val finishedShelf = mutableListOf("Legend", "Prodigy", "Champion", "Rebel")
val favoritesShelf = mutableListOf("The Darkest Minds", "Never Fade", "In the Afterlight", "The Naturals", "")
val shelves = mutableListOf(currentlyReadingShelf, wantToReadShelf, recommendedShelf, finishedShelf, favoritesShelf)

fun shelves(): String {
    for (shelf in shelves) {
        if (shelf.size != 0) {
            for (book in shelf) {
                return book
            }
            continue
        }
        when (shelf) {
            currentlyReadingShelf -> return "You aren't currently reading anything--click the Explore tab to start reading!"
            wantToReadShelf -> return "This shelf is currently empty--click the Explore tab to add books to this shelf!"
            recommendedShelf -> return "You currently don't have any recommended books :("
            finishedShelf -> return "You haven't finished any books yet :("
            favoritesShelf -> return "You don't have any favorite books yet, but don't worry!  You'll find one you really love!"
        }
    }
    return "SORRY!  The site owner hasn't gotten to this part to work yet!"
    // "ERROR!  Sorry, it seems like this isn't working correctly"
}

/**********************************************************************************************************************/
//I STILL NEED TO WORK ON THE FUNCTIONS BELOW--REQUIRES THE FRONTEND TO WORK TOO


//function that'll go into button - so if a button is clicked, this function will add the selected book onto the Want to Read shelf
// fun addBook() {
//     val selectedBook = /*idk how to get this yet*/
//     val selectedBookIndex = currentlyReadingShelf.indexOf(/*selectedBook*/)
//     currentlyReadingShelf.removeAt(selectedBookIndex)
//         currentlyReadingShelf.add()
//     /*selected shelf want to move to*/.add(/*selectedShelf.size*/, selectedBook)
// }

//this function adds a shelf after the "Add Shelf" button is clicked
// fun addShelf() {
//     val newShelf = /*INSERT WHATEVER USER TYPES INTO THE TEXTBOOK AS SHELF NAME HERE!*/
// }