package com.github.alokagrawal8.rxfirebase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.firebase.database.DataSnapshot;

@SuppressWarnings({ "WeakerAccess", "unused" }) public final class ChildEvent {

  public static final byte ADDED = 1;
  public static final byte CHANGED = 2;
  public static final byte REMOVED = 3;
  public static final byte MOVED = 4;

  @ChildEventType private final int type;
  @NonNull private final DataSnapshot dataSnapshot;
  @Nullable private final String sibling;

  ChildEvent(@ChildEventType final int type, @NonNull final DataSnapshot dataSnapshot,
      @Nullable final String sibling) {
    this.type = type;
    this.dataSnapshot = dataSnapshot;
    this.sibling = sibling;
  }

  @ChildEventType public int getType() {
    return type;
  }

  @NonNull public DataSnapshot getDataSnapshot() {
    return dataSnapshot;
  }

  @Nullable public String getSibling() {
    return sibling;
  }
}
