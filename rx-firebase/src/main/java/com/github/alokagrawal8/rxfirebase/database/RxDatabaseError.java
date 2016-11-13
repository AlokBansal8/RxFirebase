package com.github.alokagrawal8.rxfirebase.database;

import android.support.annotation.NonNull;
import com.google.firebase.database.DatabaseError;

public final class RxDatabaseError extends Throwable {

  @NonNull private final DatabaseError error;

  RxDatabaseError(@NonNull final DatabaseError error) {
    super(error.getMessage());
    this.error = error;
  }

  @NonNull public DatabaseError getError() {
    return error;
  }
}
