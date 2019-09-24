# Map


A map is a collection of mappings `key => value` such as a key `K` is associated with a value `V`.
Maps are also called `Hash maps`, `Dictionary`, `Associative Array`, `Hash table`, `Lookup table` and so on.

- An example of a map would be `{"red" => "warm", "blue" => "cold", "pink" => "warm"}`.
- Maps can hold any arbitrary key and value.
- Maps are normally implemented with hash functions.
- Production-ready Maps are quite complex, taking into consideration load factor (% of map that is full), resizing, 
`null` values, etc.

----

## Hash based maps

To use a hash function for building a map, we store the hash of the key being added and the respective value.
Example:

    key = "foo"
    value = "bar quux"
    hash(key) = 14422
    table[14422 % table.size] = value
    
Given that we mod the hash to fit the table `hash % size`, collisions are possible.
There are lots of different ways to deal with collisions, each with their own advantages and disadvantages.
Note that:
- Object key `equals()` implementation is used to check for the correct key
- Object key `hashCode()` implementation is used to determine the hash
- Thus, the map key class must implement these methods properly



## Separate chaining hash map with linked list

A chained hash map organizes its keys in a fixed table. Each key has its has computed, and the associated hash value
is stored in a linked structure.

If two keys have the same hash, the linked structure will be used to lookup the correct value, via `equals()`.

----

## Open addressing hash map

Within open addressing, the slot is determined by probing for a free index. There are different probing schemes.


## Further references

* Wikipedia on hash tables: https://en.wikipedia.org/wiki/Hash_table
* Open addressing with linear probing: https://algs4.cs.princeton.edu/34hash/LinearProbingHashST.java.html


