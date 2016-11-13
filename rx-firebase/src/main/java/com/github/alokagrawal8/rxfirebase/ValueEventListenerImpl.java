package com.github.alokagrawal8.rxfirebase;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.Iterator;
import rx.Observable;
import rx.functions.Action0;
import rx.subjects.ReplaySubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

final class ValueEventListenerImpl implements ValueEventListener {

  private final Subject<DataSnapshot, DataSnapshot> subject;
  private final DatabaseReference reference;
  private final boolean isSingleEventListener;

  ValueEventListenerImpl(@NonNull final DatabaseReference reference,
      final boolean isSingleEventListener) {
    subject = new SerializedSubject<>(ReplaySubject.<DataSnapshot>create());
    this.reference = reference;
    this.isSingleEventListener = isSingleEventListener;
  }

  @NonNull Observable<DataSnapshot> getObservable() {
    return subject.doOnSubscribe(new Action0() {
      @Override public void call() {
        if (isSingleEventListener) {
          reference.addListenerForSingleValueEvent(ValueEventListenerImpl.this);
        } else {
          reference.addValueEventListener(ValueEventListenerImpl.this);
        }
      }
    }).doOnUnsubscribe(new Action0() {
      @Override public void call() {
        if (!isSingleEventListener) reference.removeEventListener(ValueEventListenerImpl.this);
      }
    });
  }

  @Override public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
    final Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
    if (iterable == null) {
      subject.onNext(dataSnapshot);
    } else {
      final Iterator<DataSnapshot> iterator = iterable.iterator();
      while (iterator.hasNext()) subject.onNext(iterator.next());
    }
    if (isSingleEventListener) subject.onCompleted();
  }

  @Override public void onCancelled(@NonNull final DatabaseError databaseError) {
    subject.onError(new RxDatabaseError(databaseError));
  }
}
