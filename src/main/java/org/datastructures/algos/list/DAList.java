package org.datastructures.algos.list;

public interface DAList {

    /**
     * Add an element.
     * @param element the element to add
     */
    void add(Object element);

    /**
     * Remove an element.
     * @param element he element to remove
     * @return true if the element was successfully removed
     */
    boolean remove(Object element);

    /**
     * Get the object at a given position. Zero-based.
     * @param position the position
     * @return the object
     * @throws IndexOutOfBoundsException if the position is out of bounds
     */
    Object get(int position) throws IndexOutOfBoundsException;

    /**
     * Find an object's position. Zero-based.
     * @param needle the object to find
     * @return the position, or -1 if the object does not exist
     */
    int find(Object needle);

    /**
     * Gets the size of the list.
     * @return the size
     */
    int count();

    /**
     * Reverses the list in-place.
     */
    void reverse();
}
