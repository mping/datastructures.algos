package org.datastructures.algos.queue;

public interface DAQueue {

  /**
   * Adds an element to the queue
   * @param element
   */
  void offer(Object element);

  /**
   * Removes an element from the queue, or throws a {@link NullPointerException}
   * @return
   */
  Object poll();

  /**
   * Gets the next element that {@link #poll()} would return, without removing it.
   * @return
   */
  Object peek();
}
