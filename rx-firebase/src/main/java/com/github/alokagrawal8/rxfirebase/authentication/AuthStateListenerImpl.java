package com.github.alokagrawal8.rxfirebase.authentication;

import android.support.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import rx.Observable;
import rx.functions.Action0;
import rx.subjects.BehaviorSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

final class AuthStateListenerImpl implements AuthStateListener {

  private final Subject<FirebaseAuth, FirebaseAuth> subject;

  AuthStateListenerImpl() {
    subject = new SerializedSubject<>(BehaviorSubject.<FirebaseAuth>create());
  }

  Observable<FirebaseAuth> getObservable(@NonNull final FirebaseAuth auth) {
    return subject.doOnSubscribe(new Action0() {
      @Override public void call() {
        auth.addAuthStateListener(AuthStateListenerImpl.this);
      }
    }).doOnUnsubscribe(new Action0() {
      @Override public void call() {
        auth.removeAuthStateListener(AuthStateListenerImpl.this);
      }
    });
  }

  @Override public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
    subject.onNext(firebaseAuth);
  }
}
