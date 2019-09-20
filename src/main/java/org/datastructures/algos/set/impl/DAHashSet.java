package org.datastructures.algos.set.impl;

import org.datastructures.algos.set.DASet;

public class DAHashSet implements DASet {

  private Entry[] buckets;
  private int size;
  public DAHashSet() {
    buckets = new Entry[128];
    size = 0;
  }

  private int bucketIndex(Object element) {
    int hash = Math.abs(element.hashCode());
    int bucket = hash % buckets.length;
    return bucket;
  }

  @Override
  public boolean add(Object element) {
    int bucket = bucketIndex(element);
    Entry current = buckets[bucket];
    while (current != null) {
      if (current.key.equals(element)) {
        return false;
      }
      current = current.next;
    }
    Entry entry = new Entry();
    entry.key = element;
    entry.next = buckets[bucket];
    buckets[bucket] = entry;
    size++;
    return true;
  }

  @Override
  public boolean remove(Object element) {
    int bucket = bucketIndex(element);
    Entry prev = null;
    Entry current = buckets[bucket];

    while (current != null) {
      if (current.key.equals(element)) {
        if (prev == null) {
          buckets[bucket] = current.next;
        } else {
          prev.next = current.next;
        }
        prev = current;
        current = current.next;
      }
    }

    return false;
  }

  @Override
  public boolean contains(Object element) {
    int bucket = bucketIndex(element);
    Entry current = buckets[bucket];
    while (current != null) {
      if (current.key.equals(element)) {
        return true;
      }
      current = current.next;
    }
    return false;
  }

  @Override
  public int size() {
    return size;
  }

  private static class Entry {
    Object key;
    Entry next;
  }
}
