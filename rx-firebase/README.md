# RxDatabase

[![GitHub license](https://img.shields.io/github/license/dcendents/android-maven-gradle-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/AlokBansal8/RxFirebase.svg?branch=master)](https://travis-ci.org/AlokBansal8/RxFirebase)
[![Download](https://api.bintray.com/packages/alokbansal8/maven/RxFirebaseDatabase/images/download.svg)](https://bintray.com/alokbansal8/maven/RxFirebaseDatabase/_latestVersion)

RxJava wrapper for Firebase Reatime Database for Android

## Download
#### Gradle
Add the following line to your build.gradle file.
```groovy
compile 'com.github.alokagrawal8.rxfirebase:rx-database:0.1.0@aar'
```

## Usage
RxDatabase, RxReference, and RxQuery classes are equivalent of FirebaseDatabase, DatabaseReference, and Query classes in Firebase Realtime Database sdk. All the public apis in the original sdk have an equivalent here.

However, instead of using `Query.addChildEventListener`, we use `RxQuery.getChildEventListener` which returns an rxjava observable.

#### Show me the code
```java
RxDatabase.getInstance(FirebaseDatabase.getInstance())
        .getReference("child")
        .getChildEventListener()
        .subscribe(event -> {
          Log.v("TEST", "Type: " + event.getType() + ", Key: " + event.getDataSnapshot().getKey());
        });
```
*Lambda functions are not required, used in above example only for brevity.
