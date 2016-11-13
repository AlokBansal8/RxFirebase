package com.github.alokagrawal8.rxfirebase.database;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;

public final class TransactionResult {

  @NonNull private final DataSnapshot dataSnapshot;
  private final boolean complete;

  TransactionResult(@NonNull final DataSnapshot dataSnapshot, final boolean complete) {
    this.dataSnapshot = dataSnapshot;
    this.complete = complete;
  }

  @NonNull public DataSnapshot getDataSnapshot() {
    return dataSnapshot;
  }

  public boolean isComplete() {
    return complete;
  }
}
