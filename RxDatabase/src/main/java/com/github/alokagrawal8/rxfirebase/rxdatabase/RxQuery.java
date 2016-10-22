package com.github.alokagrawal8.rxfirebase.rxdatabase;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import rx.Observable;

import static com.github.alokagrawal8.rxfirebase.rxdatabase.Utils.checkNotEmpty;

@SuppressWarnings({ "WeakerAccess", "unused" }) public class RxQuery {

  protected final RxDatabase database;
  protected final DatabaseReference reference;

  private ValueEventListenerImpl valueEventListener;
  private ChildEventListenerImpl childEventListener;

  RxQuery(@NonNull final RxDatabase database, @NonNull final DatabaseReference reference) {
    this.database = database;
    this.reference = reference;
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

  public void keepSynced(final boolean keepSynced) {
    reference.keepSynced(keepSynced);
  }

  public RxQuery orderByChild(@NonNull final String child) {
    checkNotEmpty(child, "Child address cannot be empty");
    return database.get(reference.orderByChild(child).getRef());
  }

  public RxQuery limitToFirst(final int count) {
    return database.get(reference.limitToFirst(count).getRef());
  }

  public RxQuery limitToLast(final int count) {
    return database.get(reference.limitToLast(count).getRef());
  }

  public RxQuery orderByPriority() {
    return database.get(reference.orderByPriority().getRef());
  }

  public RxQuery orderByKey() {
    return database.get(reference.orderByKey().getRef());
  }

  public RxQuery orderByValue() {
    return database.get(reference.orderByValue().getRef());
  }

  public DatabaseReference getReference() {
    return reference;
  }
}
