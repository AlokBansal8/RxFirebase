package com.github.alokagrawal8.rxfirebase;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import java.util.Map;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

@SuppressWarnings({ "WeakerAccess", "unused" }) public final class RxDatabase {

  private RxDatabase() {
  }

  @NonNull @CheckResult
  public static Observable<Boolean> setValue(@NonNull final DatabaseReference reference,
      final Object o) {
    final CompletionListenerImpl listener = new CompletionListenerImpl();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        reference.setValue(o, listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<Boolean> setValue(@NonNull final DatabaseReference reference,
      final Object o, final Object o1) {
    final CompletionListenerImpl listener = new CompletionListenerImpl();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        reference.setValue(o, o1, listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<Boolean> setPriority(@NonNull final DatabaseReference reference,
      final Object o) {
    final CompletionListenerImpl listener = new CompletionListenerImpl();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        reference.setPriority(o, listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<Boolean> updateChildren(@NonNull final DatabaseReference reference,
      final Map<String, Object> map) {
    final CompletionListenerImpl listener = new CompletionListenerImpl();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        reference.updateChildren(map, listener);
      }
    });
  }

  @NonNull @CheckResult
  public static Observable<Boolean> removeValue(@NonNull final DatabaseReference reference) {
    final CompletionListenerImpl listener = new CompletionListenerImpl();
    return listener.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        reference.removeValue(listener);
      }
    });
  }

  @NonNull @CheckResult public static Observable<TransactionResult> runTransaction(
      @NonNull final DatabaseReference reference, @NonNull final Action1<MutableData> action) {
    final TransactionHandlerImpl handler = new TransactionHandlerImpl(action);
    return handler.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        reference.runTransaction(handler);
      }
    });
  }

  @NonNull @CheckResult public static Observable<TransactionResult> runTransaction(
      @NonNull final DatabaseReference reference, @NonNull final Action1<MutableData> action,
      final boolean b) {
    final TransactionHandlerImpl handler = new TransactionHandlerImpl(action);
    return handler.getObservable().doOnSubscribe(new Action0() {
      @Override public void call() {
        reference.runTransaction(handler, b);
      }
    });
  }

  @NonNull @CheckResult public static Observable<DataSnapshot> getValueEventListener(
      @NonNull final DatabaseReference reference) {
    return new ValueEventListenerImpl(reference, false).getObservable();
  }

  @NonNull @CheckResult public static Observable<DataSnapshot> getSingleValueEventListener(
      @NonNull final DatabaseReference reference) {
    return new ValueEventListenerImpl(reference, true).getObservable();
  }

  @NonNull @CheckResult public static Observable<ChildEvent> getChildEventListener(
      @NonNull final DatabaseReference reference) {
    return new ChildEventListenerImpl(reference).getObservable();
  }
}
