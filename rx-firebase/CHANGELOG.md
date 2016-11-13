# rx-database Releases

### Version 0.2.0 - November 13th 2016

* Breaking changes - all the previous public apis have been broken.
* RxDatabase class now have public static methods returning Observables.
* All the public methods related to Firebase Realtime Database which took listeners as argument have an equivalent method in RxDatabase class.
### Download
#### Gradle
Add the following line to dependencies of your build.gradle file.
```groovy
compile 'com.github.alokagrawal8.rxfirebase:rx-firebase:0.2.0@aar'
```

### Version 0.1.0 - October 26th 2016

* Using version 9.6.1 of Firebase sdk.
* Added RxDatabase to the public API which is Rx wrapper for FirebaseDatabase class in Firebase Realtime Database sdk.
* Added RxReference to the public API which is Rx wrapper for DatabaseReference class in Firebase Realtime Database sdk.
* Added RxQuery to the public API which is Rx wrapper for Query class in Firebase Realtime Database sdk.
* Almost all of the public api for above classes have been implemented except some of the Query class public api.
* RxDatabaseError class is returned when Firebase returns a DatabaseError object. The DatabaseError object is embedded in the new Throwable.
### Download
#### Gradle
Add the following line to dependencies of your build.gradle file.
```groovy
compile 'com.github.alokagrawal8.rxfirebase:rx-database:0.1.0@aar'
```