package com.github.alokagrawal8.rxfirebase.rxdatabase;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;

@SuppressWarnings({ "WeakerAccess", "unused" }) public final class TransactionResult {

  private final DataSnapshot dataSnapshot;
  private final boolean complete;

  public TransactionResult(@NonNull final DataSnapshot dataSnapshot, final boolean complete) {
    this.dataSnapshot = dataSnapshot;
    this.complete = complete;
  }

  public DataSnapshot getDataSnapshot() {
    return dataSnapshot;
  }

  public boolean isComplete() {
    return complete;
  }
}
