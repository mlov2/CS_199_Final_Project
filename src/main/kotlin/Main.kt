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
// import io.ktor.response.respondText
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
// data class UserPages(val user: String, val pageTitle: String)
data class SignIn(val username: String, val password: String, val name: String)
data class LibraryInfo(val shelf: String, val book: String, val author: String, val message: String)
// data class BookInfo(val title: String, val author: String)

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
         // //FreeMarker stuff
         //   val phrase = "my CS 199 Final Project"
         //   call.respond(FreeMarkerContent("Welcome.ftl", phrase))

            //With Mustache
            val user = SignIn("username", "password", "user")
            call.respond(MustacheContent("Welcome.html", mapOf("user" to user)))

            //Without Mustache
            // call.respondText(
            //     welcome() +
            //         "\n\n\nPlease sign in to continue." +
            //         "\nUsername: <TEXT FIELD TO TYPE USERNAME GOES HERE>" +
            //         "\nPassword: <TEXT FIELD TO TYPE PASSWORD GOES HERE>" +
            //         "\n<SIGN IN BUTTON GOES HERE>" +
            //         "\n\nDon't have an account?  Click here to sign up! <SIGN UP BUTTON GOES HERE>"
            // )
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
                //with Mustache
                val user = call.parameters["user"]
                val page = call.parameters["page"]
                val userDetails = SignIn("username", "password", "$user")
                val populate = mutableMapOf<String, Any>()
                when (page) {
                    "home" -> call.respond(MustacheContent("Home.html", mapOf("userDetails" to userDetails)))
                    "explore" -> call.respond(MustacheContent("Explore.html", mapOf("userDetails" to userDetails)))
                    "library" -> {
                        if (currentlyReadingShelf.isNotEmpty()) {
                            for ((book, author) in currentlyReadingShelf) {
                                //val bookInfo = BookInfo(book, author)
                                //println(book + "by" + author)
                                val books = LibraryInfo("Currently Reading Shelf", book, author, "")
                                populate.put("books", books)
                            }
                            println(populate)
                            populate.put("userDetails", userDetails)
                            call.respond(MustacheContent("Library.html", populate))
                        } else {
                            val emptyShelf = LibraryInfo("Currently Reading Shelf", "", "", "You aren't currently reading anything--click the Explore tab to start reading!")
                            call.respond(MustacheContent("Library.html", mapOf("emptyShelf" to emptyShelf, "userDetails" to userDetails)))
                        }
                    }
                    // "library" -> call.respond(MustacheContent("Library.html", mapOf("userDetails" to userDetails)))
                    "book buddies" -> call.respond(MustacheContent("BookBuddies.html", mapOf("userDetails" to userDetails)))
                }


                //without Mustache (original code)
                // val user = call.parameters["user"]
                // val page = call.parameters["page"]
/*                val userPage = when (page) {
                    // "home" -> "Welcome back, $user!"
                    // "explore" -> "These books might interest you, $user!"
                    "library" ->
                        "$user's Library" +
                            "\n\n\nCurrently Reading" +
                            "\n${shelves()}" +
                            "\n\nWant to Read" +
                            "\n${shelves()}" +
                            "\n\nBooks Recommended By Your Book Buddies" +
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
                }*/
                // call.respond(userPage)

                //the following can be used to return content in gson:
                //val pageDetails = UserPages(user!!, userPage)
                //call.respond(pageDetails)
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
val currentlyReadingShelf = mapOf("Moment of Truth" to "Kasie West", "Life As We Knew It" to "Susan Beth Pfeffer")
val wantToReadShelf = mapOf("Five Feet Apart" to "Rachael Lippincott")
val recommendedShelf = mapOf("The Rest of The Story" to "Sarah Dessen")
val finishedShelf = mapOf("Legend" to "Marie Lu", "Prodigy" to "Marie Lu", "Champion" to "Marie Lu", "Rebel" to "Marie Lu")
val favoritesShelf = mapOf("The Darkest Minds" to "Alexandra Bracken", "Never Fade" to "Alexandra Bracken", "In the Afterlight" to "Alexandra Bracken", "The Naturals" to "Jennifer Lynn Barnes")
val shelves = mutableListOf(currentlyReadingShelf, wantToReadShelf, recommendedShelf, finishedShelf, favoritesShelf)

//DOESN'T WORK
// fun Application.currentlyReadingShelf() {
//     install(Mustache) {
//         mustacheFactory = DefaultMustacheFactory("templates")
//     }
//
//     if (currentlyReadingShelf.isNotEmpty()) {
//         for ((book, author) in currentlyReadingShelf) {
//             //val bookInfo = BookInfo(book, author)
//             val books = LibraryInfo("Currently Reading Shelf", book, author, "")
//             MustacheContent("Library.html", mapOf("books" to books))
//             continue
//         }
//     } else {
//         val emptyShelf = LibraryInfo("Currently Reading Shelf", "", "", "You aren't currently reading anything--click the Explore tab to start reading!")
//         MustacheContent("Library.html", mapOf("emptyShelf" to emptyShelf))
//     }
// }

// fun shelves() {
//     for (shelf in shelves) {
//         if (shelf.size != 0) {
//             for (book in shelf) {
//                 val bookInfo = BookInfo(book, "author")
//                 val books = when (shelf) {
//                     currentlyReadingShelf -> LibraryInfo("Currently Reading Shelf", bookInfo.title, bookInfo.author, "")
//                     wantToReadShelf -> LibraryInfo("Want to Read", bookInfo.title, bookInfo.author, "")
//                     recommendedShelf -> LibraryInfo("Books Recommended By Book Buddies", bookInfo.title, bookInfo.author, "")
//                     finishedShelf -> LibraryInfo("Finished Reading", bookInfo.title, bookInfo.author, "")
//                     favoritesShelf -> LibraryInfo("megan's Favorites <3", bookInfo.title, bookInfo.author, "")
//                     else -> throw Exception("Um...there's a problem")
//                 }
//                 MustacheContent("Library.html", mapOf("books" to books))
//             }
//             continue
//         } else {
//             val emptyShelf = when (shelf) {
//                 currentlyReadingShelf -> LibraryInfo("Currently Reading Shelf", "", "", "You aren't currently reading anything--click the Explore tab to start reading!")
//                 wantToReadShelf -> LibraryInfo("Want to Read", "", "", "This shelf is currently empty--click the Explore tab to add books to this shelf!")
//                 recommendedShelf -> LibraryInfo("Books Recommended By Your Book Buddies", "", "","You currently don't have any recommended books :(")
//                 finishedShelf -> LibraryInfo("Finished Reading", "", "","You haven't finished any books yet :(")
//                 favoritesShelf -> LibraryInfo("megan's Favorites", "", "","You don't have any favorite books yet, but don't worry!  You'll find one you really love!")
//                 else -> throw Exception("Um...there's a problem")
//             }
//             MustacheContent("Library.html", mapOf("emptyShelf" to emptyShelf))
//         }
//     }
//     // "SORRY!  The site owner hasn't gotten to this part to work yet!"
//     // "ERROR!  Sorry, it seems like this isn't working correctly"
// }

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