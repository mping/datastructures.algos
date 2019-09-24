package org.datastructures.algos.map.impl;

import org.datastructures.algos.map.DAMap;

public class DAOpenAddressingHashMap implements DAMap {

  MapEntry[] buckets;
  int size;

  public DAOpenAddressingHashMap() {
    this.buckets = new MapEntry[16];
    this.size = 0;
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

  /**
   * Resizes the map
   */
  private void resize(int newSize) {
    MapEntry[] newBuckets = new MapEntry[newSize];
    for (MapEntry entry : buckets) {
      // we can avoid rehash if we keep the hash on the entry
      int index = bucketIndex(entry.key.hashCode());
      while (newBuckets[index] != null) {
        index++;
        index %= newBuckets.length;
      }
      newBuckets[index] = entry;
    }

    buckets = newBuckets;
  }

  @Override
  public void put(Object key, Object value) {
    MapEntry entry = new MapEntry();
    entry.key = key;
    entry.value = value;
    int index = bucketIndex(key.hashCode());

    //resize is required to ensure table has a free slot
    if ((size / (float) buckets.length) > 0.75f) {
      resize(size * 2);
    }

    int i;
    for (i = index; buckets[i] != null; i = (i + 1) % buckets.length) {
      if (buckets[i].key.equals(key)) {
        buckets[i].value = value;
        return;
      }
    }
    buckets[i] = entry;
    size++;
  }

  @Override
  public Object get(Object key) {
    for (int i = bucketIndex(key.hashCode()); buckets[i] != null; i = (i + 1) % buckets.length) {
      if (buckets[i].key.equals(key)) {
        return buckets[i].value;
      }
    }
    return null;
  }

  @Override
  public boolean remove(Object key) {
    // find position i of key
    int i = bucketIndex(key.hashCode());
    while (!key.equals(buckets[i].key)) {
      i = (i + 1) % buckets.length;
    }
    //delete
    buckets[i] = null;

    if ((size / (float) buckets.length) < 0.125f) {
      // will actually become 50% full after downsizing
      resize(size / 2);
    }
    return false;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean containsKey(Object key) {
    return false;
  }

  @Override
  public boolean containsValue(Object value) {
    return false;
  }


  static class MapEntry {
    Object key;
    Object value;
    int hash;
  }
}
