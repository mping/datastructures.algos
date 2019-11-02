# Data structures and Algorithms

This repo aims to be an **introduction** to simple data structures and algorithms.
Users are invited to provide their own implementation of the data structures and validate the implementation
through unit testing.

The implementation is provided is Java, but some things are left out:

* Collections don't implement `java.util.*` interfaces to keep it simple
 * eg: iterators are left out, `isEmpty()`, etc
* Performance is not relevant for **naive** implementations. If time allows we will delve deep into performance
* Generics are not used to keep it simple
* Ideally, Big-O notation would be **inferred** through benchmarking but that part isn't implemented yet. PRs welcome


# How?

All the tests should pass by running `./gradlew test`.

If you want to try your own implementation, run `./gradlew -q removeImpls` to delete all code 
within the `org.datastructures.algos.*.impl.*` classes. Implement your own and make sure the tests pass.

Check the `README.md` on each package to see how it is supposed to work.

# Contribute

Open an issue/PR with any kind suggestion or comment and I will look into it.
Code, typos, unit tests, spelling, copy or any other suggestions are welcome.

# TODO

 - [x] Lists 
    - [x] Linked List
    - [x] Array List
    - [ ] Persistent Lists
    - [ ] Sorting algorithms
 - [x] Sets 
 - [ ] Maps
    - [x] HashMap
      - [x] Chained
      - [x] Open addressing
    - [ ] Persistent Maps via HAMTs
 - [x] Stack
 - [x] Queue
    - [ ] Priority Queue
 - [ ] Tree 
    - [ ] Binary Tree 
    - [ ] Red/black, AVL, etc 
    - [ ] B-Tree 
    - [ ] Traversal algorithms
 - [ ] Benchmarks (WIP)
   
