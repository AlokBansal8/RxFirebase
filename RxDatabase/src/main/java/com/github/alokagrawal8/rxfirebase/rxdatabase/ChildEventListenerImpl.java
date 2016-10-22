package com.github.alokagrawal8.rxfirebase.rxdatabase;

import android.support.annotation.NonNull;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

final class ChildEventListenerImpl implements ChildEventListener {

  private final Subject<ChildEvent, ChildEvent> subject = new SerializedSubject<>(BehaviorSubject.<ChildEvent>create());

  @Override public void onChildAdded(final DataSnapshot dataSnapshot, final String s) {
    subject.onNext(new ChildEvent(ChildEvent.ADDED, dataSnapshot, s));
  }

  @Override public void onChildChanged(final DataSnapshot dataSnapshot, final String s) {
    subject.onNext(new ChildEvent(ChildEvent.CHANGED, dataSnapshot, s));
  }

  @Override public void onChildRemoved(final DataSnapshot dataSnapshot) {
    subject.onNext(new ChildEvent(ChildEvent.REMOVED, dataSnapshot, null));
  }

  @Override public void onChildMoved(final DataSnapshot dataSnapshot, final String s) {
    subject.onNext(new ChildEvent(ChildEvent.MOVED, dataSnapshot, s));
  }

  @Override public void onCancelled(DatabaseError databaseError) {
    subject.onError(new RxDatabaseError(databaseError));
  }

  @NonNull Observable<ChildEvent> getObservable() {
    return subject;
  }
}
