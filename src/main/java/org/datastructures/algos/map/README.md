# Map


A map is a collection of mappings `key => value` such as a key `K` is associated with a value `V`.
Maps are also called `Hash maps`, `Dictionary`, `Associative Array`, `Hash table`, `Lookup table` and so on.

- An example of a map would be `{"red" => "warm", "blue" => "cold", "pink" => "warm"}`.
- Maps can hold any arbitrary key and value.
- Maps are normally implemented with hash functions, for performance reasons.

----

## Hash based maps

To use a hash function for building a map, we store the hash of the key being added and the respective value.
Example:

    key = "foo"
    value = "bar quux"
    hash(key) = 14422
    table[14422 % table.size] = value
    

## Separate chaining hash map with linked list

A chained hash map organizes its keys in a fixed table. Each key has its has computed, and the associated hash value
is stored in a linked structure.

If two keys have the same hash, the linked structure will be used to lookup the correct value.

----

## Open addressing hash map



