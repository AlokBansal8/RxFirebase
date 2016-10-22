package com.github.alokagrawal8.rxfirebase.rxdatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

final class ValueEventListenerImpl implements ValueEventListener {

  private final Subject<DataSnapshot, DataSnapshot> subject =
      new SerializedSubject<>(BehaviorSubject.<DataSnapshot>create());

  private final boolean isSingleEventListener;

  ValueEventListenerImpl(final boolean isSingleEventListener) {
    this.isSingleEventListener = isSingleEventListener;
  }

  Observable<DataSnapshot> getObservable() {
    return subject;
  }

  @Override public void onDataChange(final DataSnapshot dataSnapshot) {
    subject.onNext(dataSnapshot);
    if (isSingleEventListener) subject.onCompleted();
  }

  @Override public void onCancelled(final DatabaseError databaseError) {
    subject.onError(new RxDatabaseError(databaseError));
  }
}
