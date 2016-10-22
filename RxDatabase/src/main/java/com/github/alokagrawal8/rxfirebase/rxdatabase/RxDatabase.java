package com.github.alokagrawal8.rxfirebase.rxdatabase;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.google.firebase.database.FirebaseDatabase;

@SuppressWarnings({ "WeakerAccess", "unused" }) public final class RxDatabase {

  private static RxDatabase instance;

  private final FirebaseDatabase database;

  private RxDatabase() {
    database = FirebaseDatabase.getInstance();
  }

  @CheckResult @NonNull public static RxDatabase getInstance() {
    if (instance == null) instance = new RxDatabase();
    return instance;
  }

  @CheckResult @NonNull public RxReference getReference() {
    return new RxReference(this);
  }

  @NonNull public RxDatabase setPersistenceEnabled(final boolean enabled) {
    database.setPersistenceEnabled(enabled);
    return this;
  }

  FirebaseDatabase getDatabase() {
    return database;
  }

  public void purgeOutstandingWrites() {
    database.purgeOutstandingWrites();
  }

  public void goOnline() {
    database.goOnline();
  }

  public void goOffline() {
    database.goOffline();
  }
}
