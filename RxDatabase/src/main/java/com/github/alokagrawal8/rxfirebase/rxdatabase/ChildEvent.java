package com.github.alokagrawal8.rxfirebase.rxdatabase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.firebase.database.DataSnapshot;

@SuppressWarnings({ "WeakerAccess", "unused" }) public final class ChildEvent {

  public static final byte ADDED = 1;
  public static final byte CHANGED = 2;
  public static final byte REMOVED = 3;
  public static final byte MOVED = 4;

  private final int type;
  private final DataSnapshot dataSnapshot;
  private final String string;

  ChildEvent(@ChildEventType final int type, @NonNull final DataSnapshot dataSnapshot,
      @Nullable final String string) {
    this.type = type;
    this.dataSnapshot = dataSnapshot;
    this.string = string;
  }

  public int getType() {
    return type;
  }

  @NonNull public DataSnapshot getDataSnapshot() {
    return dataSnapshot;
  }

  @Nullable public String getString() {
    return string;
  }
}
