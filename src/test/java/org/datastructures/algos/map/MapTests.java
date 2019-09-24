package org.datastructures.algos.map;

import org.datastructures.algos.map.impl.DAChainedHashMap;
import org.datastructures.algos.map.impl.DAOpenAddressingHashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class MapTests {

  private DAMap impl;

  public <T extends DAMap> MapTests(Class<T> klass) throws Throwable {
    this.impl = klass.getDeclaredConstructor().newInstance(new Object[]{});
  }

  @Parameterized.Parameters(name = "{0} implementation")
  public static Collection<Object> data() {
    return Arrays.asList(DAChainedHashMap.class, DAOpenAddressingHashMap.class);
  }

  /**
   * Empty map tests
   */

  @Test
  public void testEmptyMapSize() {
    assertEquals(0, impl.size());
  }

  @Test
  public void testEmptyMapNoKey() {
    assertEquals(null, impl.get("foo"));
  }

  @Test
  public void testEmptyMapContains() {
    assertEquals(false, impl.containsKey("foo"));
    assertEquals(false, impl.containsValue("foo"));
    assertEquals(false, impl.remove("foo"));
  }

  /**
   * Size tests
   */

  @Test
  public void testMapSize() {
    impl.put("foo", "bar");
    impl.put("quux", "quuz");
    assertEquals(2, impl.size());
  }

  @Test
  public void testMapSizeAfterRemove() {
    impl.put("foo", "bar");
    impl.put("quux", "quuz");
    impl.remove("foo");
    impl.remove("not_a_key");
    assertEquals(1, impl.size());
  }

  /**
   * Put & contains tests
   */

  @Test
  public void testPut1Elem() {
    impl.put("key", "value");

    assertEquals(true, impl.containsKey("key"));
    assertEquals(true, impl.containsValue("value"));
    assertEquals("value", impl.get("key"));
  }
  @Test
  public void testOverwrite() {
    impl.put("key", "value");
    impl.put("key", "otherValue");

    assertEquals("otherValue", impl.get("key"));
  }

  /**
   * Get tests
   */

  @Test
  public void testGet1Elem() {
    impl.put("key", "value");
    assertEquals("value", impl.get("key"));
  }

  /**
   * Remove tests
   */

  @Test
  public void testRemove1Elem() {
    impl.put("foo", "bar");
    impl.put("quux", "quuz");

    assertEquals(true, impl.remove("foo"));
    assertEquals(false, impl.containsKey("foo"));
    assertEquals(true, impl.containsKey("quux"));
  }

}
