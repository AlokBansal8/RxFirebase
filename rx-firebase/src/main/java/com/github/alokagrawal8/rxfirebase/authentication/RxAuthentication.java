package com.github.alokagrawal8.rxfirebase.authentication;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import rx.Observable;
import rx.functions.Action0;

@SuppressWarnings({ "unused", "WeakerAccess" }) public final class RxAuthentication {

  private RxAuthentication() {
  }

  @NonNull @CheckResult
  public static Observable<FirebaseAuth> getAuthListener(@NonNull final FirebaseAuth auth) {
    return new AuthStateListenerImpl().getObservable(auth);
  }

  @NonNull @CheckResult
  public static Observable<AuthResult> signInWithCredential(@NonNull final FirebaseAuth auth,
      @NonNull final AuthCredential credential) {
    final OnCompleteListenerImpl<AuthResult> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        auth.signInWithCredential(credential)
            .addOnSuccessListener(listener)
            .addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<AuthResult> signInWithCustomToken(@NonNull final FirebaseAuth auth,
      @NonNull final String token) {
    final OnCompleteListenerImpl<AuthResult> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        auth.signInWithCustomToken(token)
            .addOnSuccessListener(listener)
            .addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<AuthResult> signInWithEmailAndPassword(@NonNull final FirebaseAuth auth,
      @NonNull final String email, @NonNull final String password) {
    final OnCompleteListenerImpl<AuthResult> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener(listener)
            .addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<AuthResult> signInAnonymously(@NonNull final FirebaseAuth auth) {
    final OnCompleteListenerImpl<AuthResult> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        auth.signInAnonymously().addOnSuccessListener(listener).addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult public static Observable<AuthResult> createUserWithEmailAndPassword(
      @NonNull final FirebaseAuth auth, @NonNull final String email,
      @NonNull final String password) {
    final OnCompleteListenerImpl<AuthResult> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener(listener)
            .addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult public static Observable<ProviderQueryResult> fetchProvidersForEmail(
      @NonNull final FirebaseAuth auth, @NonNull final String email) {
    final OnCompleteListenerImpl<ProviderQueryResult> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        auth.fetchProvidersForEmail(email)
            .addOnSuccessListener(listener)
            .addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<Void> sendPasswordResetEmail(@NonNull final FirebaseAuth auth,
      @NonNull final String email) {
    final OnCompleteListenerImpl<Void> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener(listener)
            .addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<GetTokenResult> getToken(@NonNull final FirebaseUser user,
      final boolean var) {
    final OnCompleteListenerImpl<GetTokenResult> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        user.getToken(var).addOnSuccessListener(listener).addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult public static Observable<Void> reload(@NonNull final FirebaseUser user) {
    final OnCompleteListenerImpl<Void> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        user.reload().addOnSuccessListener(listener).addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<Void> reAuthenticate(@NonNull final FirebaseUser user,
      @NonNull final AuthCredential credential) {
    final OnCompleteListenerImpl<Void> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        user.reauthenticate(credential)
            .addOnSuccessListener(listener)
            .addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<AuthResult> linkWithCredential(@NonNull final FirebaseUser user,
      @NonNull final AuthCredential credential) {
    final OnCompleteListenerImpl<AuthResult> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        user.linkWithCredential(credential)
            .addOnSuccessListener(listener)
            .addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<AuthResult> unlink(@NonNull final FirebaseUser user,
      @NonNull final String s) {
    final OnCompleteListenerImpl<AuthResult> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        user.unlink(s).addOnSuccessListener(listener).addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<Void> updateProfile(@NonNull final FirebaseUser user,
      @NonNull final UserProfileChangeRequest request) {
    final OnCompleteListenerImpl<Void> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        user.updateProfile(request).addOnSuccessListener(listener).addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult public static Observable<Void> updateEmail(@NonNull final FirebaseUser user,
      @NonNull final String newEmail) {
    final OnCompleteListenerImpl<Void> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        user.updateEmail(newEmail).addOnSuccessListener(listener).addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<Void> updatePassword(@NonNull final FirebaseUser user,
      @NonNull final String newPassword) {
    final OnCompleteListenerImpl<Void> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        user.updatePassword(newPassword)
            .addOnSuccessListener(listener)
            .addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult public static Observable<Void> delete(@NonNull final FirebaseUser user) {
    final OnCompleteListenerImpl<Void> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        user.delete().addOnSuccessListener(listener).addOnFailureListener(listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<Void> sendEmailVerification(@NonNull final FirebaseUser user) {
    final OnCompleteListenerImpl<Void> listener = new OnCompleteListenerImpl<>();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        user.sendEmailVerification().addOnSuccessListener(listener).addOnFailureListener(listener);
      }
    });
  }
}
