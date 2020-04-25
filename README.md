# CS_199_Final_Project

Overview/Goal:
My final project will be using ktor to build a web application similar to Goodreads.
In my final project, I aim to include a way for users to log books they've read,
books they want to read, books they loved, and books they would recommend.  In
addition, my final project will work to include pages where users can review, browse,
and view recommended books sent to them by other users.


So far, in my Main.kt file, I have created data classes to use for
the Web API (they're not really complete though).  I have also created an
Application.userPage() function, which includes my installations for gson and
Freemarker.  I am attempting to use FreeMarker as a way to connect the front-end
and back-end parts of my application since FreeMarker allows string interpolation
in the HTML files.  I discovered FreeMarker on the ktor website.  Furthermore, I
have created two routes for my application: one that leads to the welcome page,
where it asks users to sign in, and another that leads to the user's personal page,
where there are several pages within dedicated for the features I outlined in the
overview above.  I've outlined how I want the front end to work in my
call.respondText, and I am currently trying to convert that over to the HTML files.
I still need to work on figuring out a way to connect the HTML files with my Main.kt
Kotlin file, however.  My Main.kt application currently runs on one localhost while
my HTML files run on another localhost, so I need to figure out how to combine
those onto a single localhost.  I also still need to figure out how to link the 
buttons and sidebar I created in my HTML files to my Main.kt file, which is a
challenge since I have never worked with HTML before and am thus unsure of how to
connect the two.  So far, one of the biggest challenges of creating this web
application is just figuring out how to implement what I have in my mind, and I
believe it is mostly because it incorporates parts that we have not covered in class.