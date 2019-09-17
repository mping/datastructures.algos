package org.datastructures.algos.map;

public interface DAMap {

  void put(Object key, Object value);

  Object get(Object key);

  boolean remove(Object key);

  int size();

  boolean containsKey(Object key);

  boolean containsValue(Object value);
}
