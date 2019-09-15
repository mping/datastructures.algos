package org.datastructures.algos.list;

import org.datastructures.algos.list.impl.DAArrayList;
import org.datastructures.algos.list.impl.DALinkedList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ListTests {

    @Parameterized.Parameters(name = "{0} implementation")
    public static Collection<Object> data() {
        return Arrays.asList(new Object[]{DAArrayList.class, DALinkedList.class});
    }

    public <T extends DAList> ListTests(Class<T> klass) throws IllegalAccessException, InstantiationException {
        this.impl = klass.newInstance();
    }

    private DAList impl;

    @Test
    public void testAdd1Elem() {
        impl.add("first");
        assertEquals("first", impl.get(0));
    }

    @Test
    public void testAddMultipleElems() {
        IntStream.range(0, 100).forEach(i -> impl.add(String.valueOf(i)));
        IntStream.range(0, 100).forEach(i -> assertEquals(String.valueOf(i), impl.get(i)));
    }

    @Test
    public void testRemove1Elem() {
        impl.add("first");
        impl.add("second");
        impl.add("third");

        impl.remove("second");

        assertEquals("first", impl.get(0));
        assertEquals("third", impl.get(1));
        assertEquals(2, impl.count());
    }

    @Test
    public void testRemoveNoElem() {
        impl.add("one");
        assertEquals(false, impl.remove("nonexistent"));
        assertEquals("one", impl.get(0));
    }

    @Test
    public void testGet2ndElem() {
        impl.add("first");
        impl.add("second");
        assertEquals("second", impl.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOutOfBounds() {
        impl.add("first");
        impl.add("second");
        impl.get(4);
    }

    @Test
    public void testEmptySize() {
        assertEquals(0, impl.count());
    }

    @Test
    public void testSize1El() {
        impl.add("one");
        assertEquals(1, impl.count());
    }

    @Test
    public void testFindElem() {
        impl.add("one");
        impl.add("two");
        impl.add("three");

        assertEquals(1, impl.find("two"));
    }

    @Test
    public void testFindNonExistingElem() {
        assertEquals(-1, impl.find("two"));
    }
}
