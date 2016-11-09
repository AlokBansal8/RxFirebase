package com.github.alokagrawal8.rxfirebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import java.util.HashMap;
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

public final class RxDatabaseTest {

  @Mock DatabaseError error;
  @Mock DataSnapshot snapshot;
  @Mock DatabaseReference reference;

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test public void testSetValue() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(1);
        listener.onComplete(null, reference);
        return null;
      }
    }).when(reference).setValue(any(), any(CompletionListenerImpl.class));
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(2);
        listener.onComplete(null, reference);
        return null;
      }
    }).when(reference).setValue(any(), any(), any(CompletionListenerImpl.class));

    TestSubscriber<Boolean> test1 = new TestSubscriber<>(1);
    RxDatabase.setValue(reference, "a").subscribe(test1);
    test1.assertNoErrors();
    test1.assertValueCount(1);
    test1.assertValue(Boolean.TRUE);
    test1.unsubscribe();

    TestSubscriber<Boolean> test2 = new TestSubscriber<>(1);
    RxDatabase.setValue(reference, "a", "b").subscribe(test2);
    test2.assertNoErrors();
    test2.assertValueCount(1);
    test2.assertValue(Boolean.TRUE);
    test2.unsubscribe();
  }

  @Test public void testSetValueWithError() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(1);
        listener.onComplete(error, reference);
        return null;
      }
    }).when(reference).setValue(any(), any(CompletionListenerImpl.class));
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(2);
        listener.onComplete(error, reference);
        return null;
      }
    }).when(reference).setValue(any(), any(), any(CompletionListenerImpl.class));

    TestSubscriber<Boolean> test1 = new TestSubscriber<>(1);
    RxDatabase.setValue(reference, "a").subscribe(test1);
    test1.assertTerminalEvent();
    test1.assertError(RxDatabaseError.class);
    test1.unsubscribe();

    TestSubscriber<Boolean> test2 = new TestSubscriber<>(1);
    RxDatabase.setValue(reference, "a", "b").subscribe(test2);
    test2.assertTerminalEvent();
    test2.assertError(RxDatabaseError.class);
    test2.unsubscribe();
  }

  @Test public void testSetPriority() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(1);
        listener.onComplete(null, reference);
        return null;
      }
    }).when(reference).setPriority(any(), any(CompletionListenerImpl.class));

    TestSubscriber<Boolean> test = new TestSubscriber<>(1);
    RxDatabase.setPriority(reference, "a").subscribe(test);
    test.assertNoErrors();
    test.assertValueCount(1);
    test.assertValue(Boolean.TRUE);
    test.unsubscribe();
  }

  @Test public void testSetPriorityWithError() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(1);
        listener.onComplete(error, reference);
        return null;
      }
    }).when(reference).setPriority(any(), any(CompletionListenerImpl.class));

    TestSubscriber<Boolean> test = new TestSubscriber<>(1);
    RxDatabase.setPriority(reference, "a").subscribe(test);
    test.assertTerminalEvent();
    test.assertError(RxDatabaseError.class);
    test.unsubscribe();
  }

  @Test public void testUpdateChildren() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(1);
        listener.onComplete(null, reference);
        return null;
      }
    }).when(reference)
        .updateChildren(anyMapOf(String.class, Object.class), any(CompletionListenerImpl.class));

    TestSubscriber<Boolean> test = new TestSubscriber<>(1);
    RxDatabase.updateChildren(reference, new HashMap<String, Object>()).subscribe(test);
    test.assertNoErrors();
    test.assertValueCount(1);
    test.assertValue(Boolean.TRUE);
    test.unsubscribe();
  }

  @Test public void testUpdateChildrenWithError() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(1);
        listener.onComplete(error, reference);
        return null;
      }
    }).when(reference)
        .updateChildren(anyMapOf(String.class, Object.class), any(CompletionListenerImpl.class));

    TestSubscriber<Boolean> test = new TestSubscriber<>(1);
    RxDatabase.updateChildren(reference, new HashMap<String, Object>()).subscribe(test);
    test.assertTerminalEvent();
    test.assertError(RxDatabaseError.class);
    test.unsubscribe();
  }

  @Test public void testRemoveValue() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(0);
        listener.onComplete(null, reference);
        return null;
      }
    }).when(reference).removeValue(any(CompletionListenerImpl.class));

    TestSubscriber<Boolean> test = new TestSubscriber<>(1);
    RxDatabase.removeValue(reference).subscribe(test);
    test.assertNoErrors();
    test.assertValueCount(1);
    test.assertValue(Boolean.TRUE);
    test.unsubscribe();
  }

  @Test public void testRemoveValueWithError() {
    doAnswer(new Answer<Object>() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        CompletionListener listener = invocation.getArgument(0);
        listener.onComplete(error, reference);
        return null;
      }
    }).when(reference).removeValue(any(CompletionListenerImpl.class));

    TestSubscriber<Boolean> test = new TestSubscriber<>(1);
    RxDatabase.removeValue(reference).subscribe(test);
    test.assertTerminalEvent();
    test.assertError(RxDatabaseError.class);
    test.unsubscribe();
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
    test1.assertNoErrors();
    test1.assertValueCount(1);
    assertThat(test1.getOnNextEvents().get(0).getDataSnapshot()).isEqualTo(snapshot);
    assertThat(test1.getOnNextEvents().get(0).isComplete()).isTrue();
    test1.unsubscribe();

    TestSubscriber<TransactionResult> test2 = new TestSubscriber<>(1);
    RxDatabase.runTransaction(reference, new Action1<MutableData>() {
      @Override public void call(MutableData mutableData) {
      }
    }, true).subscribe(test2);
    test2.assertNoErrors();
    test2.assertValueCount(1);
    assertThat(test2.getOnNextEvents().get(0).getDataSnapshot()).isEqualTo(snapshot);
    assertThat(test2.getOnNextEvents().get(0).isComplete()).isTrue();
    test2.unsubscribe();

    TestSubscriber<TransactionResult> test3 = new TestSubscriber<>(1);
    RxDatabase.runTransaction(reference, new Action1<MutableData>() {
      @Override public void call(MutableData mutableData) {
      }
    }, false).subscribe(test3);
    test3.assertNoErrors();
    test3.assertValueCount(1);
    assertThat(test3.getOnNextEvents().get(0).getDataSnapshot()).isEqualTo(snapshot);
    assertThat(test3.getOnNextEvents().get(0).isComplete()).isFalse();
    test3.unsubscribe();
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
    test1.assertTerminalEvent();
    test1.assertError(RxDatabaseError.class);
    test1.unsubscribe();

    TestSubscriber<TransactionResult> test2 = new TestSubscriber<>(1);
    RxDatabase.runTransaction(reference, new Action1<MutableData>() {
      @Override public void call(MutableData mutableData) {
      }
    }, true).subscribe(test2);
    test2.assertTerminalEvent();
    test2.assertError(RxDatabaseError.class);
    test2.unsubscribe();

    TestSubscriber<TransactionResult> test3 = new TestSubscriber<>(1);
    RxDatabase.runTransaction(reference, new Action1<MutableData>() {
      @Override public void call(MutableData mutableData) {
      }
    }, false).subscribe(test3);
    test3.assertTerminalEvent();
    test3.assertError(RxDatabaseError.class);
    test3.unsubscribe();
  }
}
