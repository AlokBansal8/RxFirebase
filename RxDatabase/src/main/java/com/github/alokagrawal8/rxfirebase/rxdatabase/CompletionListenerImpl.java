package com.github.alokagrawal8.rxfirebase.rxdatabase;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

final class CompletionListenerImpl implements DatabaseReference.CompletionListener {

  private final Subject<Boolean, Boolean> subject =
      new SerializedSubject<>(BehaviorSubject.<Boolean>create());

  CompletionListenerImpl() {
  }

  Observable<Boolean> getObservable() {
    return subject;
  }

  @Override
  public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
    if (databaseError == null) {
      subject.onNext(Boolean.TRUE);
    } else {
      subject.onError(new RxDatabaseError(databaseError));
    }
  }
}
