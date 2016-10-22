package com.github.alokagrawal8.rxfirebase.rxdatabase;

import android.support.annotation.NonNull;
import com.google.firebase.database.DatabaseError;

public final class RxDatabaseError extends Throwable {

  @NonNull private final DatabaseError error;

  RxDatabaseError(@NonNull final DatabaseError error) {
    this.error = error;
  }

  @NonNull public DatabaseError getError() {
    return error;
  }
}
