package com.github.alokagrawal8.rxfirebase.rxdatabase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

final class Utils {

  private Utils() {
  }

  static void checkNotNull(@Nullable final String s, @NonNull final String message) {
    if (s == null || s.isEmpty()) throw new NullPointerException(message);
  }
}
