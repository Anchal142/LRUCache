package org.anchal;

public interface LruCache<K, V> {
    V get(K key);
    void put(K key, V value);
    int size();
    int capacity();
    long hits();
    long misses();
    long evictions();
}
