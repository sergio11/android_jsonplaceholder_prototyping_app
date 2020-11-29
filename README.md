# Android {JSON} Placeholder Prototyping App

## Main Goals

* Clean architecure approach.
* Dependency injection through Dagger.
* Asynchronous operations management with coroutines.
* Using Material Design guidelines.
* Using local storage baseond on Jetpack Room Library to implement cache layer through repository pattern.
* Testing with mockito-kotlin, assertj, junit, robolectric mock web server..

## Points to be assess.

* Post detail is retrieved only once, next calls get the information from local storage.

<img  src="./images/network_inspector.PNG" />

* Also, I have configured a HTTP cache layer to give support to the ETAG mechanism. If a second post has the same author that has been retrieved before, is not necessary to get the same data again.

<img src="./images/network_inspector_2.PNG" />

* You can see with the Android Studio database inspector how the data is structured to persist locally.

<img src="./images/database_inspector.PNG" />

* Unit tests have been implemented to validate the logic implemented in the repository layer

<img src="./images/test.PNG" />


## Screenshots

<img width="250px" align="left" src="./images/app_gift.gif" />


