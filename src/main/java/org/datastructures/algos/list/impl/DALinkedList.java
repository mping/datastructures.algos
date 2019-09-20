package org.datastructures.algos.list.impl;


import org.datastructures.algos.list.DAList;

public class DALinkedList implements DAList {

  Node root;
  int size;
  public DALinkedList() {
    root = null;
    size = 0;
  }

  @Override
  public void add(Object element) {
    if (root == null) {
      root = new Node(element);
    } else {
      Node curr = root;
      while (curr.next != null) {
        curr = curr.next;
      }
      curr.next = new Node(element);
    }
    size++;
  }

  @Override
  public boolean remove(Object element) {
    Node prev = null;
    Node curr = root;
    if (curr == null) {
      return false;
    }
    do {
      if (curr.val.equals(element)) {

        if (prev == null) {
          root = root.next;
        } else {
          prev.next = curr.next;
        }

        size--;
        return true;
      }

      prev = curr;
      curr = curr.next;

    } while (curr != null);
    return false;
  }

  @Override
  public Object get(int position) throws IndexOutOfBoundsException {
    if (position >= size) throw new IndexOutOfBoundsException("Out of bounds: " + position);

    int i = 0;
    Node curr = root;
    while (i < position) {
      curr = curr.next;
      i++;
    }
    return curr.val;
  }

  @Override
  public int find(Object element) {
    if (size == 0) {
      return -1;
    }
    Node curr = root;
    for (int i = 0; i < size; i++) {
      if (curr.val.equals(element)) {
        return i;
      }
      curr = curr.next;
    }
    return -1;
  }

  @Override
  public int size() {
    return size;
  }

  private static class Node {
    Object val;
    Node next;

    Node(Object value) {
      val = value;
    }
  }

}
