package com.github.alokagrawal8.rxfirebase.authentication;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

public final class RxAuthenticationTest {

  @Mock FirebaseAuth firebaseAuth;
  @Mock AuthCredential credential;

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test public void testAuthStateListener() {
    doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        AuthStateListener listener = invocation.getArgument(0);
        listener.onAuthStateChanged(firebaseAuth);
        return null;
      }
    }).when(firebaseAuth).addAuthStateListener(any(AuthStateListener.class));

    final TestSubscriber<FirebaseAuth> test = new TestSubscriber<>();
    RxAuthentication.getAuthListener(firebaseAuth).subscribe(test);
    test.assertNoErrors();
    test.assertNotCompleted();
    test.assertNoTerminalEvent();
    test.assertValueCount(1);
    test.assertValue(firebaseAuth);
    test.unsubscribe();
    test.assertUnsubscribed();
  }

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored") @Test
  public void testOnCompleteListenerImpl() {
    final OnCompleteListenerImpl<FirebaseAuth> listener1 = new OnCompleteListenerImpl<>();
    final TestSubscriber<FirebaseAuth> test1 = new TestSubscriber<>();
    listener1.getObservable().subscribe(test1);
    test1.assertNoErrors();
    test1.assertNotCompleted();
    test1.assertValueCount(0);
    listener1.onSuccess(firebaseAuth);
    test1.assertValueCount(1);
    test1.assertValue(firebaseAuth);
    test1.assertCompleted();
    test1.assertTerminalEvent();
    test1.assertUnsubscribed();

    final OnCompleteListenerImpl<Void> listener2 = new OnCompleteListenerImpl<>();
    final TestSubscriber<Void> test2 = new TestSubscriber<>();
    listener2.getObservable().subscribe(test2);
    test2.assertNoErrors();
    test2.assertNotCompleted();
    test2.assertValueCount(0);
    listener2.onSuccess(null);
    test2.assertValueCount(0);
    test2.assertCompleted();
    test2.assertTerminalEvent();
    test2.assertUnsubscribed();

    final OnCompleteListenerImpl<Void> listener3 = new OnCompleteListenerImpl<>();
    final TestSubscriber<Void> test3 = new TestSubscriber<>();
    listener3.getObservable().subscribe(test3);
    test3.assertNoErrors();
    test3.assertNotCompleted();
    test3.assertValueCount(0);
    listener3.onFailure(new RuntimeException("a"));
    test3.assertValueCount(0);
    test3.assertTerminalEvent();
    test3.assertUnsubscribed();
    test3.assertError(RuntimeException.class);
    assertThat(test3.getOnErrorEvents().get(0).getMessage()).isEqualTo("a");
  }
}
