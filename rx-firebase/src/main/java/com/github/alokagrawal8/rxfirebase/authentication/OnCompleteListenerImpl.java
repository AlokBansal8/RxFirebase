package com.github.alokagrawal8.rxfirebase.authentication;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import rx.Observable;
import rx.subjects.ReplaySubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

final class OnCompleteListenerImpl<T> implements OnSuccessListener<T>, OnFailureListener {

  private final Subject<T, T> subject;

  OnCompleteListenerImpl() {
    subject = new SerializedSubject<>(ReplaySubject.<T>create());
  }

  Observable<T> getObservable() {
    return subject;
  }

  @Override public void onSuccess(@Nullable final T t) {
    if (!(t instanceof Void || t == null)) subject.onNext(t);
    subject.onCompleted();
  }

  @Override public void onFailure(@NonNull final Exception e) {
    subject.onError(e);
  }
}
