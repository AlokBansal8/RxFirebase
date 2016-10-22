package com.github.alokagrawal8.rxfirebase.rxdatabase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public final class RxDatabaseTest {

  @Mock FirebaseDatabase database;
  @Mock DatabaseReference reference;

  private RxDatabase rxDatabase;

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);
    when(database.getReference()).thenReturn(reference);
    when(database.getReference(anyString())).thenReturn(reference);
    when(database.getReferenceFromUrl(anyString())).thenReturn(reference);
    rxDatabase = RxDatabase.getInstance(database);
  }

  @SuppressWarnings("CheckResult") @Test public void emptyStringThrows() {
    try {
      rxDatabase.getReference("");
      fail();
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("Child address is empty string");
    }
    try {
      rxDatabase.getReferenceFromUrl("");
      fail();
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("Url is empty string");
    }
  }

  @Test public void rxReferenceGetters() {
    assertThat(rxDatabase.getReference()).isNotNull();
    assertThat(rxDatabase.getReference("child")).isNotNull();
    assertThat(rxDatabase.getReferenceFromUrl("child")).isNotNull();
  }

  @Test public void referenceMap() {
    final RxReference rxReference = new RxReference(rxDatabase, reference);
    assertThat(rxReference).isNotNull();
    assertThat(rxDatabase.get(reference)).isEqualTo(rxReference);
  }
}
