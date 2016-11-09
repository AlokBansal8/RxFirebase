package com.github.alokagrawal8.rxfirebase;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

class CompletionListenerImpl implements DatabaseReference.CompletionListener {

  private final Subject<Boolean, Boolean> subject =
      new SerializedSubject<>(BehaviorSubject.<Boolean>create());

  CompletionListenerImpl() {
  }

  Observable<Boolean> getObservable() {
    return subject;
  }

  @Override public void onComplete(final DatabaseError databaseError,
      final DatabaseReference databaseReference) {
    if (databaseError == null) {
      subject.onNext(Boolean.TRUE);
      Observable.timer(10, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
        @Override public void call(Long aLong) {
          subject.onCompleted();
        }
      });
    } else {
      subject.onError(new RxDatabaseError(databaseError));
    }
  }
}
