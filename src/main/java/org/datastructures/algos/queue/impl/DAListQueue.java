package org.datastructures.algos.queue.impl;

import org.datastructures.algos.queue.DAQueue;

import java.util.LinkedList;
import java.util.List;

public class DAListQueue implements DAQueue {

  List<Object> list = new LinkedList<>();

  @Override
  public void offer(Object element) {
    list.add(element);
  }

  @Override
  public Object poll() {
    if (list.size() == 0) {
      throw new NullPointerException("Queue is empty");
    }
    return list.remove(0);
  }

  @Override
  public Object peek() {
    if (list.size() == 0) {
      return null;
    }
    return list.get(0);
  }

  @Override
  public int size() {
    return list.size();
  }
}
