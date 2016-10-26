# rx-database Releases

### Version 0.1.0 - October 26th 2016

* Using version 9.6.1 of Firebase sdk.
* Added RxDatabase to the public API which is Rx wrapper for FirebaseDatabase class in Firebase Realtime Database sdk.
* Added RxReference to the public API which is Rx wrapper for DatabaseReference class in Firebase Realtime Database sdk.
* Added RxQuery to the public API which is Rx wrapper for Query class in Firebase Realtime Database sdk.
* Almost all of the public api for above classes have been implemented except some of the Query class public api.
* RxDatabaseError class is returned when Firebase returns a DatabaseError object. The DatabaseError object is embedded in the new Throwable.