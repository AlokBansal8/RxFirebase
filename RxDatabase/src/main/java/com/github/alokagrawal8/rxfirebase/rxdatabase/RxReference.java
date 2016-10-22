package com.github.alokagrawal8.rxfirebase.rxdatabase;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import java.util.Map;
import rx.Observable;
import rx.functions.Action1;

import static com.github.alokagrawal8.rxfirebase.rxdatabase.Utils.checkNotEmpty;

@SuppressWarnings({ "WeakerAccess", "unused" }) public final class RxReference extends RxQuery {

  RxReference(@NonNull final RxDatabase database, @NonNull final DatabaseReference reference) {
    super(database, reference);
    database.addToMap(reference, this);
  }

  public static void goOnline() {
    DatabaseReference.goOnline();
  }

  public static void goOffline() {
    DatabaseReference.goOffline();
  }

  @CheckResult @NonNull public RxReference child(@NonNull final String childPath) {
    checkNotEmpty(childPath, "Child address is empty string");
    return database.get(reference.child(childPath));
  }

  @CheckResult @NonNull public RxReference push() {
    return database.get(reference.push());
  }

  @NonNull public Observable<Boolean> setValue(final Object o) {
    final CompletionListenerImpl listener = new CompletionListenerImpl();
    reference.setValue(o, listener);
    return listener.getObservable();
  }

  @NonNull public Observable<Boolean> setValue(final Object o, final Object o1) {
    final CompletionListenerImpl listener = new CompletionListenerImpl();
    reference.setValue(o, o1, listener);
    return listener.getObservable();
  }

  @NonNull public Observable<Boolean> setPriority(final Object o) {
    final CompletionListenerImpl listener = new CompletionListenerImpl();
    reference.setPriority(o, listener);
    return listener.getObservable();
  }

  @NonNull public Observable<Boolean> updateChildren(Map<String, Object> map) {
    final CompletionListenerImpl listener = new CompletionListenerImpl();
    reference.updateChildren(map, listener);
    return listener.getObservable();
  }

  @NonNull public Observable<Boolean> removeValue() {
    final CompletionListenerImpl listener = new CompletionListenerImpl();
    reference.removeValue(listener);
    return listener.getObservable();
  }

  @NonNull
  public Observable<TransactionResult> runTransaction(@NonNull final Action1<MutableData> action) {
    final TransactionHandlerImpl handler = new TransactionHandlerImpl(action);
    reference.runTransaction(handler);
    return handler.getObservable();
  }

  @NonNull
  public Observable<TransactionResult> runTransaction(@NonNull final Action1<MutableData> action,
      final boolean b) {
    final TransactionHandlerImpl handler = new TransactionHandlerImpl(action);
    reference.runTransaction(handler, b);
    return handler.getObservable();
  }

  public RxDatabase getDatabase() {
    return database;
  }

  public RxReference getRoot() {
    return database.get(reference.getRoot());
  }

  public RxReference getParent() {
    return database.get(reference.getParent());
  }

  public String getKey() {
    return reference.getKey();
  }
}
