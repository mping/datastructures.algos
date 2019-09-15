package org.datastructures.algos.set;

public interface DASet {

    /**
     * Adds an element to the set.
     * @param element
     */
    boolean add(Object element);

    /**
     * Removes an element from the set.
     * @param element
     * @return
     */
    boolean remove(Object element);

    /**
     * Indicates if an element is contained in the set.
     * @param element
     * @return
     */
    boolean contains(Object element);

    /**
     * Returns the size of the set.
     * @return
     */
    int size();
}
