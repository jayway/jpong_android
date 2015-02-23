# Pong UI for android

Agenda for 2015-02-27 competences-day.

Try out some frameworks for fetching images/json data over the network and for annotating your app (getting rid of boilerplate code).

Frameworks:

Dagger2 - http://google.github.io/dagger/ - an annotation / injection framework

Android annotations - http://androidannotations.org/ - an annotation / injection framework

Volley - http://developer.android.com/training/volley/index.html - Networking

Picaso - http://square.github.io/picasso/ - Image downloading and handling

Butterknife - http://jakewharton.github.io/butterknife/ - an annotation / injection framework

Spring - http://projects.spring.io/spring-android/ - Networking

Robodjuce - https://github.com/roboguice/roboguice - an annotation / injection framework

Know of any other frameworks? add them here!


1. Fetch data from http://jaywaypongserver.herokuapp.com/match_history.json and populate the "Math history" page using volley or spring (or android annotations that also supports some networking via annotations).
The server can be found at https://github.com/jayway/KHelgGameServer if you want to run if localy.

2. Stop using the logo from the res folder and fetch the logo from the server/internet, http://jaywaypongserver.herokuapp.com/jay.png or for example http://placekitten.com/202/202

3. Remove all boilerplate code and use annotations to inject stuff.

4. Implement a chat page in the client. More info on the chat feature of the server can be found in the server READ.ME
