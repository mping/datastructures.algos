package org.datastructures.algos.set;

import org.datastructures.algos.set.impl.DAHashSet;
import org.datastructures.algos.set.impl.DAListSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SetTests {

    @Parameterized.Parameters(name = "{0} implementation")
    public static Collection<Object> data() {
        return Arrays.asList(new Object[]{DAListSet.class, DAHashSet.class});
    }

    public <T extends DASet> SetTests(Class<T> klass) throws IllegalAccessException, InstantiationException {
        this.impl = klass.newInstance();
    }

    private DASet impl;

    @Test
    public void testEmptySet() {
        assertEquals(0, impl.size());
    }

    @Test
    public void testAdd1Element() {
        assertEquals(true, impl.add("one"));
        assertEquals(true, impl.contains("one"));
    }

    @Test
    public void testDoesNotContain() {
        assertEquals(false, impl.contains("some"));
    }


    @Test
    public void testDuplicateAdd() {
        impl.add("one");
        impl.add("one");
        impl.add("one");
        assertEquals(1, impl.size());
    }

    @Test
    public void testRemoveElement() {
        impl.add("one");
        impl.add("two");

        impl.remove("one");
        assertEquals(false, impl.contains("one"));
        assertEquals(true, impl.contains("two"));
    }

    @Test
    public void testAddMultipleElems() {
        IntStream.range(0, 100).forEach(i -> impl.add(String.valueOf(i)));
        IntStream.range(0, 100).forEach(i -> assertEquals(true, impl.contains(String.valueOf(i))));

        assertEquals(false, impl.contains("100"));
    }

}
