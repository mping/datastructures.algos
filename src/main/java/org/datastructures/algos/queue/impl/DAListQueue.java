package org.datastructures.algos.queue.impl;

import org.datastructures.algos.queue.DAQueue;

public class DAListQueue implements DAQueue {

  DAListQueue.Node root;
  DAListQueue.Node tail;
  int size;

  public DAListQueue() {
    root = null;
    size = 0;
  }

  private static class Node {
    Object val;
    DAListQueue.Node next;

    Node(Object value) {
      val = value;
    }
  }


  @Override
  public void offer(Object element) {
  }

  @Override
  public Object poll() {
    return null;
  }

  @Override
  public Object peek() {
    return null;
  }
}
