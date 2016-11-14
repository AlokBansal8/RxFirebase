# RxFirebase

[![GitHub license](https://img.shields.io/github/license/dcendents/android-maven-gradle-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/AlokBansal8/RxFirebase.svg?branch=master)](https://travis-ci.org/AlokBansal8/RxFirebase)
[![Download](https://api.bintray.com/packages/alokbansal8/maven/RxFirebase/images/download.svg)](https://bintray.com/alokbansal8/maven/RxFirebase/_latestVersion)


RxJava wrapper for Firebase for Android.
This library allow you to use Observable model of RxJava with Firebase instead of all the different kind of listeners.

Currently, the library supports following from the Firebase Suite:
- Firebase Realtime Database
- Firebase Authentication.

## Usage
Setup the Firebase project and SDK as per the [docs](https://firebase.google.com/docs/android/setup).

Use [RxDatabase](rx-firebase/src/main/java/com/github/alokagrawal8/rxfirebase/database/RxDatabase.java) and [RxAuthentication](rx-firebase/src/main/java/com/github/alokagrawal8/rxfirebase/authentication/RxAuthentication.java) classes to get the RxJava equivalent for similar APIs in official library.
Both the classes have ``public static`` methods for such tasks.
#### Show me the code
###### Write data
```java
RxDatabase.setValue(databaseReference, value)
          .subscribe(isSuccessful -> Log.v(TAG, "Write operation: " + isSuccessful));
```
###### Read data
```java
RxDatabase.getValueEventListener(databaseReference)
          .subscribe(dataSnapshot -> {
            // Do Something
          }, e -> Log.e(TAG, e.getMessage(), e));

RxDatabase.getChildEventListener(databaseReference)
          .subscribe(childEvent -> {
            // Do Something
            // Notice ChildEvent object instead of DataSnapshot type
          }, e -> Log.e(TAG, e.getMessage(), e))
```
###### Authenticate
```java
RxAuthentication.getAuthListener(FirebaseAuth.getInstance())
          .subscribe(auth -> {
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
              // User is signed in
              Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
              // User is signed out
              Log.d(TAG, "onAuthStateChanged:signed_out");
            }
            // ...
          });
```

## Download
#### Gradle
Add the following line to your build.gradle file.
```groovy
compile 'com.github.alokagrawal8.rxfirebase:rx-firebase:0.3.0@aar'
```

## License

    Copyright 2016 Alok Bansal

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
