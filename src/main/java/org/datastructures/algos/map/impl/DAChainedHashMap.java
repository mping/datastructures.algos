package org.datastructures.algos.map.impl;

import org.datastructures.algos.map.DAMap;

public class DAChainedHashMap implements DAMap {

  private MapEntry[] buckets;
  private int size;

  public DAChainedHashMap() {
    // if the initial table is small, there will be a lot of collisions
    // because we are taking the hash MODULO length
    //
    // if the initial table is large, there can be lots of empty rows
    // production-grade (ie, real) hash maps keep track of the percentage of rows that are "full"
    // and are able to resize the internal table dynamically
    buckets = new MapEntry[16];
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
    MapEntry mapEntry = new MapEntry();
    mapEntry.key = key;
    mapEntry.value = value;
    mapEntry.hash = Math.abs(key.hashCode());

    int index = bucketIndex(mapEntry.hash);

    if (buckets[index] == null) {
      buckets[index] = mapEntry;
    } else {
      // here we add the map entry to the head of the linked map entries, but we could have added it to the end
      MapEntry curr = buckets[index];
      buckets[index] = mapEntry;
      mapEntry.next = curr;
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
        return true;
      }
    }

    return false;
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
