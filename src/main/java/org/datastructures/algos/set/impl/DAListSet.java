package org.datastructures.algos.set.impl;

import org.datastructures.algos.set.DASet;

import java.util.ArrayList;
import java.util.List;

public class DAListSet implements DASet {

    List<Object> list = new ArrayList<Object>();

    @Override
    public boolean add(Object element) {
        if(!list.contains(element)) {
            list.add(element);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object element) {
        return list.remove(element);
    }

    @Override
    public boolean contains(Object element) {
        return list.contains(element);
    }

    @Override
    public int size() {
        return list.size();
    }
}
