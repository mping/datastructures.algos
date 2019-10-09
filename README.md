# Data structures and Algorithms

This repo aims to be an **introduction** to simple data structures and algorithms.
Users are invited to provide their own implementation of the data structures and validate the implementation
through unit testing.

The implementation is provided is Java, but some things are left out:

* Collections don't implement `java.util.*` interfaces to keep it simple
 * eg: iterators are left out, `isEmpty()`, etc
* Generics are not used to keep it simple
* Ideally, Big-O notation would be *inferred* through benchmarking but that part isn't implemented yet. PRs welcome


# How?

Just delete all code within the `org.datastructures.algos.*.impl.*` classes and implement your own.
All unit tests should pass.

Check the `README.md` on each package to see how it is supposed to work.

# Contribute

Open an issue/PR with any kind suggestion or comment and I will look into it.
Code, typos, spelling, copy or any other suggestions are welcome.

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
 - [ ] Queue
    - [ ] Priority Queue
 - [ ] Tree 
    - [ ] Binary Tree 
    - [ ] Red/black, AVL, etc 
    - [ ] B-Tree 
    - [ ] Traversal algorithms
 - [ ] Benchmarks (WIP)
   
