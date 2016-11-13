package com.github.alokagrawal8.rxfirebase;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import rx.Observable;
import rx.subjects.ReplaySubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

class CompletionListenerImpl implements DatabaseReference.CompletionListener {

  private final Subject<Boolean, Boolean> subject;

  CompletionListenerImpl() {
    subject = new SerializedSubject<>(ReplaySubject.<Boolean>create());
  }

  Observable<Boolean> getObservable() {
    return subject;
  }

  @Override public void onComplete(final DatabaseError databaseError,
      final DatabaseReference databaseReference) {
    if (databaseError == null) {
      subject.onNext(Boolean.TRUE);
      subject.onCompleted();
    } else {
      subject.onError(new RxDatabaseError(databaseError));
    }
  }
}
