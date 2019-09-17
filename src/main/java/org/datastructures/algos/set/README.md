# Set

A set is an *unordered* collection of elements. A set `{1,2,3}` is equivalent to `{3,2,1}`, which is equivalent to `{1,3,2}` and so on.
Adding an existing element to a set doesn't change the set. A set doesn't allow for duplicates.


## List backed set

It should be simple to implement a set via a list. The only special consideration is to disallow duplicates. 


## Hash based set

Uses a hash function to keep track of duplicates.