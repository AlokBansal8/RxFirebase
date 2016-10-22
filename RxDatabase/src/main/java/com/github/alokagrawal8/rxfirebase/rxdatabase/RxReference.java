package com.github.alokagrawal8.rxfirebase.rxdatabase;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import java.util.Map;
import rx.Observable;

import static com.github.alokagrawal8.rxfirebase.rxdatabase.Utils.checkNotEmpty;

@SuppressWarnings({ "WeakerAccess", "unused" }) public final class RxReference {

  private final RxDatabase database;
  private final DatabaseReference reference;

  private ValueEventListenerImpl valueEventListener;
  private ChildEventListenerImpl childEventListener;

  RxReference(@NonNull final RxDatabase database, @NonNull final DatabaseReference reference) {
    this.database = database;
    this.reference = reference;
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

  @NonNull public Observable<DataSnapshot> getValueEventListener() {
    if (valueEventListener == null) {
      valueEventListener = new ValueEventListenerImpl(false);
      reference.addValueEventListener(valueEventListener);
    }
    return valueEventListener.getObservable();
  }

  @NonNull public Observable<DataSnapshot> getSingleValueEventListener() {
    final ValueEventListenerImpl listener = new ValueEventListenerImpl(true);
    reference.addListenerForSingleValueEvent(listener);
    return listener.getObservable();
  }

  @NonNull public Observable<ChildEvent> getChildEventListener() {
    if (childEventListener == null) {
      childEventListener = new ChildEventListenerImpl();
      reference.addChildEventListener(childEventListener);
    }
    return childEventListener.getObservable();
  }

  public void removeListeners() {
    if (valueEventListener != null) reference.removeEventListener(valueEventListener);
    if (childEventListener != null) reference.removeEventListener(childEventListener);
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
