package org.datastructures.algos.stack;

import org.datastructures.algos.stack.impl.DAListStack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class StackTests {

  private DAStack impl;

  public <T extends DAStack> StackTests(Class<T> klass) throws Throwable {
    this.impl = klass.getDeclaredConstructor().newInstance(new Object[]{});
  }

  @Parameterized.Parameters(name = "{0} implementation")
  public static Collection<Object> data() {
    return Arrays.asList(DAListStack.class);
  }

  // empty stack tests

  @Test
  public void testInitialStackIsEmpty() {
    assertTrue(impl.isEmpty());
  }

  @Test(expected = NullPointerException.class)
  public void testPopEmptyStackThrows() {
    impl.pop();
  }

  @Test
  public void testPeekEmptyReturnsNull() {
    assertNull(impl.peek());
  }

  // non empty tests

  @Test
  public void testPushMakesStackNotEmpty() {
    impl.push("top");
    assertFalse(impl.isEmpty());
  }

  @Test
  public void testPushTwiceWorks() {
    impl.push("bottom");
    impl.push("top");

    assertEquals("top", impl.peek());
  }

  @Test
  public void testPopIsReverseOrder() {
    impl.push("1");
    impl.push("2");
    impl.push("3");

    assertEquals("3", impl.pop());
    assertEquals("2", impl.pop());
    assertEquals("1", impl.pop());
  }

}
