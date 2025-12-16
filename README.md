# LRU Cache Implementation (Java)

## Overview
This project implements an **in-memory Least Recently Used (LRU) Cache**
with **O(1)** average time complexity for `get` and `put` operations.

The implementation follows a **production-style design**, meets all functional
and non-functional requirements, and is written using **Java 17**.

---

## Requirements Covered
- O(1) average time complexity for `get` and `put`
- Fixed capacity cache with LRU eviction policy
- Thread-safe implementation
- No usage of `LinkedHashMap`
- Observability via metrics (hits, misses, evictions)
- Comprehensive unit testing using JUnit 5

---

## Public API
```java
public interface LruCache<K, V> {
    V get(K key);          // returns null if not found
    void put(K key, V value);
    int size();            // current size
    int capacity();        // max size

    long hits();
    long misses();
    long evictions();
}
