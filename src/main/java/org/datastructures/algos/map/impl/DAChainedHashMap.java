package org.datastructures.algos.map.impl;

import org.datastructures.algos.map.DAMap;

public class DAChainedHashMap implements DAMap {

  @Override
  public boolean containsValue(Object value) {
    return false;
  }

  static class MapEntry {
    Object key;
    Object value;
    MapEntry next;
  }

  private MapEntry[] buckets;
  private int size;

  /**
   * Given a key, calculate its index on the buckets table
   *
   * @param key the key
   * @return the index
   */
  private int bucketIndex(Object key) {
    int hash = Math.abs(key.hashCode());
    int index = hash % buckets.length;
    return index;
  }

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
  public void put(Object key, Object value) {
    MapEntry mapEntry = new MapEntry();
    mapEntry.key = key;
    mapEntry.value = value;

    int index = bucketIndex(key);

    if (buckets[index] == null) {
      buckets[index] = mapEntry;
    } else {
      // here we add the map entry to the head of the linked map entries, but we could have added it to the end
      MapEntry curr = buckets[index];
      buckets[index] = mapEntry;
      mapEntry.next = curr;
    }
  }

  @Override
  public Object get(Object key) {
    return null;
  }

  @Override
  public boolean remove(Object key) {
    return false;
  }


  @Override
  public boolean containsKey(Object key) {
    return false;
  }

  @Override
  public int size() {
    return size;
  }
}
