# List


A list is an *ordered* collection of elements. Example: `(1, 3, 5, 10, 20, 1)`. 
- A list can have repeated elements.
- A list allows for random access of an element given its index.
- A list can grow or shrink dynamically.

Lists are also called `Array`, `Vector`, `Sequence`, etc. although these names kind of leak the underlying implementation.
 

----

## Array backed list

This list is backed by an array. An array is a contiguous section of memory, where the elements are next to each other.
Special consideration is needed for two cases:


##### Array full

If you have **full** array with size 4 `[1,2,3,4]` and want to add an extra element, you need to allocate more space.

* to add "5" to: `[1,2,3,4]`
* allocate new space: `[1,2,3,4,_,_,_,_]`
  * normally we double the space, but you can increase the space in any way you like
* add the new element: `[1,2,3,4,5,_,_,_]`


##### Removing elements

If you have an array `[1, 2, 3, 4]` and want to remove element `2`, the rest of the elements need to "shift" to the left:

* before: `[1, 2, 3, 4]`
  * element "2" removed: `[1, _ , 3, 4]`
* after:  `[1, 3, 4]`


----

## Single linked list

A single linked list is a data structure where each value sits in a node, and each node points to the next one.
A single linked list can be used to implement all `List` operations.


    ----------    ----------    ----------
    | val: 1 | -> | val: 2 | -> | val: 3 | -> ...  
    ---------     ---------     ---------

