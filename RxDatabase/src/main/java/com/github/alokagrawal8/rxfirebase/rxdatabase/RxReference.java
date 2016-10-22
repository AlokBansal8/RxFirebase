package com.github.alokagrawal8.rxfirebase.rxdatabase;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.google.firebase.database.DatabaseReference;

import static com.github.alokagrawal8.rxfirebase.rxdatabase.Utils.checkNotNull;

@SuppressWarnings({ "WeakerAccess", "unused" }) public final class RxReference {

  private DatabaseReference childReference;

  RxReference(@NonNull final RxDatabase database) {
    childReference = database.getDatabase().getReference();
  }

  @CheckResult @NonNull public RxReference child(@NonNull final String child) {
    checkNotNull(child, "Child address is null or empty string");
    childReference = childReference.child(child);
    return this;
  }
}
