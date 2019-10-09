package org.datastructures.algos.stack.impl;

import org.datastructures.algos.stack.DAStack;

import java.util.LinkedList;
import java.util.List;

public class DAListStack implements DAStack {

  List<Object> list = new LinkedList<>();

  @Override
  public void push(Object element) {
    list.add(element);
  }

  @Override
  public Object pop() {
    if (isEmpty()) {
      throw new NullPointerException("Empty stack");
    }
    return list.remove(list.size()-1);
  }

  @Override
  public Object peek() {
    return isEmpty() ? null : list.get(list.size() - 1);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }
}
