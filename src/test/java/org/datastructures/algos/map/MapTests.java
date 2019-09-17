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

  @Parameterized.Parameters(name = "{0} implementation")
  public static Collection<Object> data() {
    return Arrays.asList(new Object[]{DAChainedHashMap.class, DAOpenAddressingHashMap.class});
  }

  public <T extends DAMap> MapTests(Class<T> klass) throws IllegalAccessException, InstantiationException {
    this.impl = klass.newInstance();
  }

  private DAMap impl;

  @Test
  public void testEmptyMap() {
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

  @Test
  public void testPut1Elem() {
    impl.put("key", "value");

    assertEquals(true, impl.containsKey("key"));
    assertEquals(true, impl.containsValue("value"));
    assertEquals("value", impl.get("key"));
  }

  @Test
  public void testRemove1Elem() {
    impl.put("foo", "bar");
    impl.put("quux", "quuz");

    assertEquals(true, impl.remove("foo"));
    assertEquals(false, impl.containsKey("foo"));
  }

}
