package com.github.alokagrawal8.rxfirebase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.github.alokagrawal8.rxfirebase.database.ChildEvent;
import com.github.alokagrawal8.rxfirebase.database.ChildEventType;
import com.github.alokagrawal8.rxfirebase.database.RxDatabase;
import com.github.alokagrawal8.rxfirebase.database.RxDatabaseError;
import com.github.alokagrawal8.rxfirebase.database.TransactionResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import rx.functions.Action1;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyMapOf;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored") public final class RxDatabaseTest {

  private final String ERROR_MESSAGE = "Test Error";
  private final String SNAPSHOT_KEY = "TestKey";

  @Mock DatabaseError error;
  @Mock DataSnapshot snapshot;
  @Mock DatabaseReference reference;

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);
    when(error.getMessage()).thenReturn(ERROR_MESSAGE);
    when(snapshot.getKey()).thenReturn(SNAPSHOT_KEY);
  }

  @Test public void testSetValue() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(1);
        listener.onComplete(null, reference);
        return null;
      }
    }).when(reference).setValue(any(), any(CompletionListener.class));
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(2);
        listener.onComplete(null, reference);
        return null;
      }
    }).when(reference).setValue(any(), any(), any(CompletionListener.class));

    TestSubscriber<Boolean> test1 = new TestSubscriber<>(1);
    RxDatabase.setValue(reference, "a").subscribe(test1);
    withoutErrorSubscriberAssertions(test1);

    TestSubscriber<Boolean> test2 = new TestSubscriber<>(1);
    RxDatabase.setValue(reference, "a", "b").subscribe(test2);
    withoutErrorSubscriberAssertions(test2);
  }

  @Test public void testSetValueWithError() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(1);
        listener.onComplete(error, reference);
        return null;
      }
    }).when(reference).setValue(any(), any(CompletionListener.class));
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(2);
        listener.onComplete(error, reference);
        return null;
      }
    }).when(reference).setValue(any(), any(), any(CompletionListener.class));

    TestSubscriber<Boolean> test1 = new TestSubscriber<>(1);
    RxDatabase.setValue(reference, "a").subscribe(test1);
    withErrorSubscriberAssertions(test1);

    TestSubscriber<Boolean> test2 = new TestSubscriber<>(1);
    RxDatabase.setValue(reference, "a", "b").subscribe(test2);
    withErrorSubscriberAssertions(test2);
  }

  @Test public void testSetPriority() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(1);
        listener.onComplete(null, reference);
        return null;
      }
    }).when(reference).setPriority(any(), any(CompletionListener.class));

    TestSubscriber<Boolean> test = new TestSubscriber<>(1);
    RxDatabase.setPriority(reference, "a").subscribe(test);
    withoutErrorSubscriberAssertions(test);
  }

  @Test public void testSetPriorityWithError() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(1);
        listener.onComplete(error, reference);
        return null;
      }
    }).when(reference).setPriority(any(), any(CompletionListener.class));

    TestSubscriber<Boolean> test = new TestSubscriber<>(1);
    RxDatabase.setPriority(reference, "a").subscribe(test);
    withErrorSubscriberAssertions(test);
  }

  @Test public void testUpdateChildren() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(1);
        listener.onComplete(null, reference);
        return null;
      }
    }).when(reference)
        .updateChildren(anyMapOf(String.class, Object.class), any(CompletionListener.class));

    TestSubscriber<Boolean> test = new TestSubscriber<>(1);
    RxDatabase.updateChildren(reference, new HashMap<String, Object>()).subscribe(test);
    withoutErrorSubscriberAssertions(test);
  }

  @Test public void testUpdateChildrenWithError() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(1);
        listener.onComplete(error, reference);
        return null;
      }
    }).when(reference)
        .updateChildren(anyMapOf(String.class, Object.class), any(CompletionListener.class));

    TestSubscriber<Boolean> test = new TestSubscriber<>(1);
    RxDatabase.updateChildren(reference, new HashMap<String, Object>()).subscribe(test);
    withErrorSubscriberAssertions(test);
  }

  @Test public void testRemoveValue() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(0);
        listener.onComplete(null, reference);
        return null;
      }
    }).when(reference).removeValue(any(CompletionListener.class));

    TestSubscriber<Boolean> test = new TestSubscriber<>(1);
    RxDatabase.removeValue(reference).subscribe(test);
    withoutErrorSubscriberAssertions(test);
  }

  @Test public void testRemoveValueWithError() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(0);
        listener.onComplete(error, reference);
        return null;
      }
    }).when(reference).removeValue(any(CompletionListener.class));

    TestSubscriber<Boolean> test = new TestSubscriber<>(1);
    RxDatabase.removeValue(reference).subscribe(test);
    withErrorSubscriberAssertions(test);
  }

  @Test public void testRunTransaction() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        Transaction.Handler handler = invocation.getArgument(0);
        handler.onComplete(null, true, snapshot);
        return null;
      }
    }).when(reference).runTransaction(any(Transaction.Handler.class));
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        Transaction.Handler handler = invocation.getArgument(0);
        boolean b = invocation.getArgument(1);
        handler.onComplete(null, b, snapshot);
        return null;
      }
    }).when(reference).runTransaction(any(Transaction.Handler.class), anyBoolean());

    TestSubscriber<TransactionResult> test1 = new TestSubscriber<>(1);
    RxDatabase.runTransaction(reference, new Action1<MutableData>() {
      @Override public void call(MutableData mutableData) {
      }
    }).subscribe(test1);
    withoutErrorTransactionSubscriberAssertions(test1, true);

    TestSubscriber<TransactionResult> test2 = new TestSubscriber<>(1);
    RxDatabase.runTransaction(reference, new Action1<MutableData>() {
      @Override public void call(MutableData mutableData) {
      }
    }, true).subscribe(test2);
    withoutErrorTransactionSubscriberAssertions(test2, true);

    TestSubscriber<TransactionResult> test3 = new TestSubscriber<>(1);
    RxDatabase.runTransaction(reference, new Action1<MutableData>() {
      @Override public void call(MutableData mutableData) {
      }
    }, false).subscribe(test3);
    withoutErrorTransactionSubscriberAssertions(test3, false);
  }

  @Test public void testRunTransactionWithError() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        Transaction.Handler handler = invocation.getArgument(0);
        handler.onComplete(error, true, snapshot);
        return null;
      }
    }).when(reference).runTransaction(any(Transaction.Handler.class));
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        Transaction.Handler handler = invocation.getArgument(0);
        boolean b = invocation.getArgument(1);
        handler.onComplete(error, b, snapshot);
        return null;
      }
    }).when(reference).runTransaction(any(Transaction.Handler.class), anyBoolean());

    TestSubscriber<TransactionResult> test1 = new TestSubscriber<>(1);
    RxDatabase.runTransaction(reference, new Action1<MutableData>() {
      @Override public void call(MutableData mutableData) {
      }
    }).subscribe(test1);
    withErrorSubscriberAssertions(test1);

    TestSubscriber<TransactionResult> test2 = new TestSubscriber<>(1);
    RxDatabase.runTransaction(reference, new Action1<MutableData>() {
      @Override public void call(MutableData mutableData) {
      }
    }, true).subscribe(test2);
    withErrorSubscriberAssertions(test2);

    TestSubscriber<TransactionResult> test3 = new TestSubscriber<>(1);
    RxDatabase.runTransaction(reference, new Action1<MutableData>() {
      @Override public void call(MutableData mutableData) {
      }
    }, false).subscribe(test3);
    withErrorSubscriberAssertions(test3);
  }

  @Test public void testValueEventListener() {
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ValueEventListener listener = invocation.getArgument(0);
        listener.onDataChange(snapshot);
        return null;
      }
    }).when(reference).addValueEventListener(any(ValueEventListener.class));

    when(snapshot.getChildren()).thenReturn(null);
    TestSubscriber<DataSnapshot> test1 = new TestSubscriber<>();
    RxDatabase.getValueEventListener(reference).subscribe(test1);
    test1.assertNoErrors();
    test1.assertValueCount(1);
    test1.assertValue(snapshot);
    assertThat(test1.isUnsubscribed()).isFalse();
    assertThat(test1.getOnNextEvents().get(0).getKey()).isEqualTo(SNAPSHOT_KEY);
    test1.unsubscribe();
    test1.assertUnsubscribed();

    when(snapshot.getChildren()).thenReturn(new Iterable<DataSnapshot>() {
      @Override public Iterator<DataSnapshot> iterator() {
        return new Iterator<DataSnapshot>() {
          int count = 0;

          @Override public boolean hasNext() {
            return count < 3;
          }

          @Override public DataSnapshot next() {
            count++;
            return snapshot;
          }
        };
      }
    });
    TestSubscriber<DataSnapshot> test2 = new TestSubscriber<>();
    RxDatabase.getValueEventListener(reference).subscribe(test2);
    test2.assertNoErrors();
    test2.assertValueCount(3);
    assertThat(test2.isUnsubscribed()).isFalse();
    for (final DataSnapshot data : test2.getOnNextEvents()) {
      assertThat(data).isEqualTo(snapshot);
      assertThat(data.getKey()).isEqualTo(SNAPSHOT_KEY);
    }
    test2.unsubscribe();
    test2.assertUnsubscribed();
  }

  @Test public void testSingleValueEventListener() {
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ValueEventListener listener = invocation.getArgument(0);
        listener.onDataChange(snapshot);
        return null;
      }
    }).when(reference).addListenerForSingleValueEvent(any(ValueEventListener.class));

    when(snapshot.getChildren()).thenReturn(null);
    TestSubscriber<DataSnapshot> test1 = new TestSubscriber<>();
    RxDatabase.getSingleValueEventListener(reference).subscribe(test1);
    test1.assertTerminalEvent();
    test1.assertUnsubscribed();
    test1.assertCompleted();
    test1.assertNoErrors();
    test1.assertValueCount(1);
    test1.assertValue(snapshot);
    assertThat(test1.getOnNextEvents().get(0).getKey()).isEqualTo(SNAPSHOT_KEY);

    when(snapshot.getChildren()).thenReturn(new Iterable<DataSnapshot>() {
      @Override public Iterator<DataSnapshot> iterator() {
        return new Iterator<DataSnapshot>() {
          int count = 0;

          @Override public boolean hasNext() {
            return count < 3;
          }

          @Override public DataSnapshot next() {
            count++;
            return snapshot;
          }
        };
      }
    });
    TestSubscriber<DataSnapshot> test2 = new TestSubscriber<>();
    RxDatabase.getSingleValueEventListener(reference).subscribe(test2);
    test2.assertTerminalEvent();
    test2.assertUnsubscribed();
    test2.assertCompleted();
    test2.assertNoErrors();
    test2.assertValueCount(3);
    for (final DataSnapshot data : test2.getOnNextEvents()) {
      assertThat(data).isEqualTo(snapshot);
      assertThat(data.getKey()).isEqualTo(SNAPSHOT_KEY);
    }
  }

  @Test public void testChildEventListener() {
    final String sibling = "a";
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ChildEventListener listener = invocation.getArgument(0);
        listener.onChildAdded(snapshot, sibling);
        return null;
      }
    }).when(reference).addChildEventListener(any(ChildEventListener.class));

    TestSubscriber<ChildEvent> test1 = new TestSubscriber<>();
    RxDatabase.getChildEventListener(reference).subscribe(test1);
    withoutErrorChildEventListenerSubscriberAssertions(test1, ChildEvent.ADDED, sibling);

    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ChildEventListener listener = invocation.getArgument(0);
        listener.onChildChanged(snapshot, sibling);
        return null;
      }
    }).when(reference).addChildEventListener(any(ChildEventListener.class));

    TestSubscriber<ChildEvent> test2 = new TestSubscriber<>();
    RxDatabase.getChildEventListener(reference).subscribe(test2);
    withoutErrorChildEventListenerSubscriberAssertions(test2, ChildEvent.CHANGED, sibling);

    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ChildEventListener listener = invocation.getArgument(0);
        listener.onChildRemoved(snapshot);
        return null;
      }
    }).when(reference).addChildEventListener(any(ChildEventListener.class));

    TestSubscriber<ChildEvent> test3 = new TestSubscriber<>();
    RxDatabase.getChildEventListener(reference).subscribe(test3);
    withoutErrorChildEventListenerSubscriberAssertions(test3, ChildEvent.REMOVED, null);

    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ChildEventListener listener = invocation.getArgument(0);
        listener.onChildMoved(snapshot, sibling);
        return null;
      }
    }).when(reference).addChildEventListener(any(ChildEventListener.class));

    TestSubscriber<ChildEvent> test4 = new TestSubscriber<>();
    RxDatabase.getChildEventListener(reference).subscribe(test4);
    withoutErrorChildEventListenerSubscriberAssertions(test4, ChildEvent.MOVED, sibling);
  }

  @Test public void testEventListenerWithError() {
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ValueEventListener listener = invocation.getArgument(0);
        listener.onCancelled(error);
        return null;
      }
    }).when(reference).addListenerForSingleValueEvent(any(ValueEventListener.class));
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ValueEventListener listener = invocation.getArgument(0);
        listener.onCancelled(error);
        return null;
      }
    }).when(reference).addValueEventListener(any(ValueEventListener.class));
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        ChildEventListener listener = invocation.getArgument(0);
        listener.onCancelled(error);
        return null;
      }
    }).when(reference).addChildEventListener(any(ChildEventListener.class));

    TestSubscriber<DataSnapshot> test1 = new TestSubscriber<>();
    RxDatabase.getValueEventListener(reference).subscribe(test1);
    withErrorSubscriberAssertions(test1);

    TestSubscriber<DataSnapshot> test2 = new TestSubscriber<>();
    RxDatabase.getSingleValueEventListener(reference).subscribe(test2);
    withErrorSubscriberAssertions(test2);

    TestSubscriber<ChildEvent> test3 = new TestSubscriber<>();
    RxDatabase.getChildEventListener(reference).subscribe(test3);
    withErrorSubscriberAssertions(test3);
  }

  private void withoutErrorSubscriberAssertions(@NonNull final TestSubscriber<Boolean> test) {
    test.assertTerminalEvent();
    test.assertUnsubscribed();
    test.assertCompleted();
    test.assertNoErrors();
    test.assertValueCount(1);
    test.assertValue(Boolean.TRUE);
  }

  private void withoutErrorTransactionSubscriberAssertions(
      @NonNull final TestSubscriber<TransactionResult> test, final boolean value) {
    test.assertTerminalEvent();
    test.assertUnsubscribed();
    test.assertCompleted();
    test.assertNoErrors();
    test.assertValueCount(1);
    assertThat(test.getOnNextEvents().get(0).getDataSnapshot()).isEqualTo(snapshot);
    assertThat(test.getOnNextEvents().get(0).isComplete()).isEqualTo(value);
  }

  private void withoutErrorChildEventListenerSubscriberAssertions(
      @NonNull final TestSubscriber<ChildEvent> test, @ChildEventType final int type,
      @Nullable final String sibling) {
    test.assertNoErrors();
    test.assertNoTerminalEvent();
    assertThat(test.isUnsubscribed()).isFalse();
    test.assertValueCount(1);
    final ChildEvent event = test.getOnNextEvents().get(0);
    assertThat(event.getDataSnapshot()).isEqualTo(snapshot);
    assertThat(event.getType()).isEqualTo(type);
    assertThat(event.getSibling()).isEqualTo(sibling);
    test.unsubscribe();
    test.assertUnsubscribed();
  }

  private void withErrorSubscriberAssertions(@NonNull final TestSubscriber test) {
    test.assertTerminalEvent();
    test.assertUnsubscribed();
    test.assertError(RxDatabaseError.class);
    final RxDatabaseError throwable = (RxDatabaseError) test.getOnErrorEvents().get(0);
    assertThat(throwable.getMessage()).isEqualTo(ERROR_MESSAGE);
    assertThat(throwable.getError()).isEqualTo(error);
  }
}
