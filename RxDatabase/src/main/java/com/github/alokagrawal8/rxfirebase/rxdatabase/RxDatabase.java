package com.github.alokagrawal8.rxfirebase.rxdatabase;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger.Level;

import static com.github.alokagrawal8.rxfirebase.rxdatabase.Utils.checkNotEmpty;

@SuppressWarnings({ "WeakerAccess", "unused" }) public final class RxDatabase {

  private static RxDatabase instance;

  private final FirebaseDatabase database;
  private final ArrayMap<DatabaseReference, RxReference> referenceMap;

  private RxDatabase() {
    database = FirebaseDatabase.getInstance();
    referenceMap = new ArrayMap<>();
  }

  @CheckResult @NonNull public static RxDatabase getInstance() {
    if (instance == null) instance = new RxDatabase();
    return instance;
  }

  @CheckResult @NonNull public RxReference getReference() {
    return new RxReference(this, database.getReference());
  }

  @CheckResult @NonNull public RxReference getReference(@NonNull final String childPath) {
    checkNotEmpty(childPath, "Child address is empty string");
    return new RxReference(this, database.getReference(childPath));
  }

  @CheckResult @NonNull public RxReference getReferenceFromUrl(@NonNull final String url) {
    checkNotEmpty(url, "Url is empty string");
    return new RxReference(this, database.getReferenceFromUrl(url));
  }

  @NonNull public RxDatabase setPersistenceEnabled(final boolean enabled) {
    database.setPersistenceEnabled(enabled);
    return this;
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

  public void setLogLevel(Level level) {
    database.setLogLevel(level);
  }

  void addToMap(@NonNull final DatabaseReference reference,
      @NonNull final RxReference rxReference) {
    referenceMap.put(reference, rxReference);
  }

  RxReference get(@Nullable final DatabaseReference reference) {
    if (reference == null) return null;
    RxReference rxReference = referenceMap.get(reference);
    if (rxReference == null) rxReference = new RxReference(this, reference);
    return rxReference;
  }
}
