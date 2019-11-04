package org.datastructures.algos.queue;

import org.datastructures.algos.queue.impl.DAListQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class QueueTests {

  private DAQueue impl;

  public <T extends DAQueue> QueueTests(Class<T> klass) throws Throwable {
    this.impl = klass.getDeclaredConstructor().newInstance(new Object[]{});
  }

  @Parameterized.Parameters(name = "{0} implementation")
  public static Collection<Object> data() {
    return Arrays.asList(DAListQueue.class);
  }

  // empty stack tests
  @Test
  public void testPeekReturnsNull() {
    assertNull(impl.peek());
  }

  @Test(expected = NullPointerException.class)
  public void testPollThrowsNpe() {
    impl.poll();
  }

  @Test
  public void offerThenPeekWorks() {
    impl.offer("first");
    assertEquals("first", impl.peek());
  }

  @Test
  public void offerThenPollWorks() {
    impl.offer("first");
    assertEquals("first", impl.poll());
    assertNull(impl.peek());
  }

  @Test
  public void offerOrderingIsFifo() {
    impl.offer("first");
    impl.offer("second");
    impl.offer("third");

    assertEquals("first", impl.poll());
    assertEquals("second", impl.poll());
    assertEquals("third", impl.poll());
    assertNull(impl.peek());
  }

}
