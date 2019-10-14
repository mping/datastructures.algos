package org.datastructures.algos.queue;

import org.datastructures.algos.queue.impl.DAListQueue;
import org.datastructures.algos.stack.impl.DAListStack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

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

  @Test
  public void testEmptyQueueSize() {
    assertEquals(0, impl.size());
  }

  @Test(expected = NullPointerException.class)
  public void testEmptyQueuePollThrowsNPE() {
    impl.poll();
  }

  @Test
  public void testEmptyQueuePeekReturnsNull() {
    assertNull(impl.peek());
  }

  @Test
  public void testFIFOOrdering() {
    impl.offer("first");
    impl.offer("second");
    impl.offer("third");

    assertEquals("first", impl.poll());
    assertEquals("second", impl.poll());
    assertEquals("third", impl.poll());
  }

}
