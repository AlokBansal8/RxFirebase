package com.github.alokagrawal8.rxfirebase.rxdatabase;

import android.support.annotation.NonNull;

final class Utils {

  private Utils() {
  }

  static void checkNotEmpty(@NonNull final String s, @NonNull final String message) {
    if (s.isEmpty()) throw new NullPointerException(message);
  }
}
