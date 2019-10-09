package org.datastructures.algos.stack;

public interface DAStack {

  /**
   * Adds an element to the top of the stack
   *
   * @param element
   */
  void push(Object element);

  /**
   * Pops the last element from the stack.
   *
   * @return
   */
  Object pop();

  /**
   * Gets the element on top of the stack, or null
   *
   * @return
   */
  Object peek();

  /**
   * Returns true if the stack is empty
   *
   * @return
   */
  boolean isEmpty();
}
