package com.github.alokagrawal8.rxfirebase.rxdatabase;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import rx.Observable;

import static com.github.alokagrawal8.rxfirebase.rxdatabase.Utils.checkNotNull;

@SuppressWarnings({ "WeakerAccess", "unused" }) public final class RxReference {

  private DatabaseReference childReference;
  private ValueEventListenerImpl valueEventListener;
  private ChildEventListenerImpl childEventListener;

  RxReference(@NonNull final RxDatabase database) {
    childReference = database.getDatabase().getReference();
  }

  @CheckResult @NonNull public RxReference child(@NonNull final String child) {
    checkNotNull(child, "Child address is null or empty string");
    childReference = childReference.child(child);
    return this;
  }

  @NonNull public Observable<Boolean> setValue(final Object o) {
    final CompletionListenerImpl listener = new CompletionListenerImpl();
    childReference.setValue(o, listener);
    return listener.getObservable();
  }

  @NonNull public Observable<Boolean> setValue(final Object o, final Object o1) {
    final CompletionListenerImpl listener = new CompletionListenerImpl();
    childReference.setValue(o, o1, listener);
    return listener.getObservable();
  }

  @NonNull public Observable<DataSnapshot> getValueEventListener() {
    if (valueEventListener == null) {
      valueEventListener = new ValueEventListenerImpl(false);
      childReference.addValueEventListener(valueEventListener);
    }
    return valueEventListener.getObservable();
  }

  @NonNull public Observable<DataSnapshot> getSingleValueEventListener() {
    final ValueEventListenerImpl listener = new ValueEventListenerImpl(true);
    childReference.addListenerForSingleValueEvent(listener);
    return listener.getObservable();
  }

  @NonNull public Observable<ChildEvent> getChildEventListener() {
    if (childEventListener == null) {
      childEventListener = new ChildEventListenerImpl();
      childReference.addChildEventListener(childEventListener);
    }
    return childEventListener.getObservable();
  }
}
