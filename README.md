# CS_199_Final_Project

Overview/Goal:
For my final project, I used ktor to try to build a web application similar to
Goodreads.  Since I love to read, I thought it would be cool to recreate something
that related to reading and books.  When starting this project, my goal was to
include a way for users to log books they've read, books they want to read, books
they loved, and books they would recommend.  In addition, I wanted to include
pages where users could review, browse, and view recommended books sent to them by
other users.  However, I had faced several obstacles while working on this project and
was thus unable to get my web application working like I envisioned.  Below, I will
explain the details of my code for my web application, tasks I was able to accomplish,
and the challenges I faced.


The Details of My Web Application:


The Challenges I Faced:
FreeMarker: While working on my web application for the second checkpoint, I realized
that I needed a way to connect the front-end and back-end parts of my application.
I had already created two routes for my web application: one that led to the welcome
page, which prompted users to sign in, and another that led to the user's personal
page, which had several pages within it dedicated to some of the features I had
described above in my overview.  I had outlined in the ```call.respondText```
for these routes how I wanted the front end to work and look like, and after quickly
learning the basics of HTML, I began converting what I had in the ```call.respondText```
over to HTML files, which I found would allow me to construct the front-end much
better.  I discovered on the ktor website the template FreeMarker and attempted to use
it as a way to connect the front-end and back-end parts of my application since it
allows string interpolation in HTML files.  However, after following the instructions
on the ktor website to install FreeMarker, I was having trouble using its feature of
string interpolation.  I am not sure if it was because the template did not install
properly in my application or if it was because of some other reason, but I was unable
to access the .ftl file, which I believe allows you to use FreeMarker in your
application.  I tried looking online for a way to fix my problem, but I could not
find anything, so I decided to try another template called Mustache, which worked
out much better.
