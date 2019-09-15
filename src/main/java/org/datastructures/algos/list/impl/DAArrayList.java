package org.datastructures.algos.list.impl;


import org.datastructures.algos.list.DAList;

import java.util.Arrays;

public class DAArrayList implements DAList {

    Object[] array;
    int size;

    public DAArrayList() {
        array = new Object[8];
        size = 0;
    }

    @Override
    public void add(Object element) {
        // if we reached the limit, grow the array
        if (array.length == size + 1) {
            array = Arrays.copyOf(array, array.length * 2);
        }
        array[size] = element;
        size++;
    }

    @Override
    public boolean remove(Object element) {
        int pos = find(element);
        if (pos < 0) {
            return false;
        }
        System.arraycopy(array, pos + 1, array, pos, size - 1 - pos);
        size = size - 1;
        return true;
    }

    @Override
    public Object get(int position) throws IndexOutOfBoundsException {
        if (position >= size) {
            throw new IndexOutOfBoundsException("Out of bounds: " + position);
        }
        return array[position];
    }

    @Override
    public int find(Object element) {
        for (int i = 0; i < size; i++) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

}
