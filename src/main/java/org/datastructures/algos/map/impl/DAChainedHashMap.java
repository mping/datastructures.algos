package org.datastructures.algos.map.impl;

import org.datastructures.algos.map.DAMap;

public class DAChainedHashMap implements DAMap {

  private int initialSize = 16;
  private MapEntry[] buckets;
  private int size;

  public DAChainedHashMap() {
    // if the initial table is small, there will be a lot of collisions
    // because we are taking the hash MODULO length
    //
    // if the initial table is large, there can be lots of empty rows
    // production-grade (ie, real) hash maps keep track of the percentage of rows that are "full"
    // and are able to resize the internal table dynamically
    buckets = new MapEntry[initialSize];
  }

  @Override
  public boolean containsValue(Object value) {
    for (int i = 0; i < buckets.length; i++) {
      MapEntry entry;
      if ((entry = buckets[i]) != null) {
        while (entry != null) {
          if (entry.value != null && entry.value.equals(value)) {
            return true;
          }
          entry = entry.next;
        }
      }
    }
    return false;
  }

  private void resize(int newSize) {
    MapEntry[] newBuckets = new MapEntry[newSize];
    for (int i = 0; i < buckets.length; i++) {
      if (buckets[i] != null) {
        MapEntry entry = buckets[i];
        while (entry != null) {
          // reuse the hash
          int index = entry.hash % newBuckets.length;
          newBuckets[index] = entry;
          entry = entry.next;
        }
      }
    }
  }

  /**
   * Given a hash, calculate its index on the buckets table
   *
   * @param hash the key
   * @return the index
   */
  private int bucketIndex(int hash) {
    int index = hash % buckets.length;
    return index;
  }

  @Override
  public void put(Object key, Object value) {
    int hash = Math.abs(key.hashCode());
    int index = bucketIndex(hash);

    if (size / (float) buckets.length > 0.75f) {
      resize(buckets.length * 2);
    }

    if (buckets[index] == null) {
      MapEntry mapEntry = new MapEntry();
      mapEntry.key = key;
      mapEntry.value = value;
      mapEntry.hash = hash;
      buckets[index] = mapEntry;
    } else {
      MapEntry prev = null;
      MapEntry curr = buckets[index];
      while (curr != null && !curr.key.equals(key)) {
        prev = curr;
        curr = curr.next;
      }
      if (curr == null) {
        MapEntry mapEntry = new MapEntry();
        mapEntry.key = key;
        mapEntry.value = value;
        mapEntry.hash = hash;
        prev.next = mapEntry;
      } else {
        curr.value = value;
      }
    }
    size++;
  }

  @Override
  public Object get(Object key) {
    int hash = Math.abs(key.hashCode());
    int index = bucketIndex(hash);

    MapEntry entry = buckets[index];
    while (entry != null) {
      if (entry.hash == hash) {
        return entry.value;
      }
    }

    return null;
  }

  @Override
  public boolean remove(Object key) {
    int hash = Math.abs(key.hashCode());
    int index = bucketIndex(hash);
    boolean found = false;

    MapEntry entry = buckets[index];
    MapEntry prev = null;
    while (entry != null) {
      if (entry.hash == hash) {
        if (prev == null) {
          buckets[index] = null;
        } else {
          prev.next = entry.next;
        }
        size--;
        found = true;
        break;
      }
    }

    if (buckets.length != initialSize && size / (float) buckets.length < 0.125f) {
      resize(buckets.length / 2);
    }

    return found;
  }

  @Override
  public boolean containsKey(Object key) {
    return get(key) != null;
  }

  @Override
  public int size() {
    return size;
  }

  static class MapEntry {
    Object key;
    Object value;
    int hash;
    MapEntry next;
  }
}
