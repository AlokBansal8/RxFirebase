package com.github.alokagrawal8.rxfirebase.rxdatabase;

import android.support.annotation.NonNull;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import rx.Observable;
import rx.functions.Action0;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

final class ChildEventListenerImpl implements ChildEventListener {

  private final Subject<ChildEvent, ChildEvent> subject;
  private final DatabaseReference reference;

  ChildEventListenerImpl(@NonNull final DatabaseReference reference) {
    subject = new SerializedSubject<>(PublishSubject.<ChildEvent>create());
    this.reference = reference;
  }

  @NonNull Observable<ChildEvent> getObservable() {
    return subject.doOnUnsubscribe(new Action0() {
      @Override public void call() {
        reference.removeEventListener(ChildEventListenerImpl.this);
      }
    });
  }

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
}