# List


A list is an *ordered* collection of elements. Example: `[1, 3, 5, 10, 20, 1]`.
Elements can appear more than once.


----

## Array backed list

This list is backed by an array. Special consideration is needed for two cases:

##### Array full

If you have **full** array with size 4 `[1,2,3,4]` and want to add an extra element, you need to allocate more space.

##### Removing elements

If you have an array `[1, 2, 3, 4]` and want to remove element `2`, the rest of the elements need to "shift" to the left:

* before: `[1, 2, 3, 4]`
* "2" removed: `[1, _ , 3, 4]`
* after:  `[1, 3, 4]`


----

## Single linked list

A single linked list is a data structure where each value sits in a node, and each node points to the next one.


    ----------    ----------    ----------
    | val: 1 | -> | val: 2 | -> | val: 3 | -> ...  
    ---------     ---------     ---------

