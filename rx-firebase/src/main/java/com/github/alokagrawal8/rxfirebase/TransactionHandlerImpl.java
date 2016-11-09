package com.github.alokagrawal8.rxfirebase;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

final class TransactionHandlerImpl implements Transaction.Handler {

  private final Subject<TransactionResult, TransactionResult> subject =
      new SerializedSubject<>(BehaviorSubject.<TransactionResult>create());
  private final Action1<MutableData> action;

  TransactionHandlerImpl(@NonNull final Action1<MutableData> action) {
    this.action = action;
  }

  Observable<TransactionResult> getObservable() {
    return subject;
  }

  @Override public Transaction.Result doTransaction(final MutableData mutableData) {
    action.call(mutableData);
    return Transaction.success(mutableData);
  }

  @Override public void onComplete(final DatabaseError databaseError, final boolean b,
      final DataSnapshot dataSnapshot) {
    if (databaseError == null) {
      subject.onNext(new TransactionResult(dataSnapshot, b));
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
