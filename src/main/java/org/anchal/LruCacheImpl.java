package org.anchal;


import java.util.HashMap;
import java.util.Map;

public class LruCacheImpl<K, V> implements LruCache<K, V> {

    private class Node {
        K key;
        V value;
        Node prev;
        Node next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private int size = 0;

    private final Map<K, Node> cache = new HashMap<>();

    private final Node head = new Node(null, null);
    private final Node tail = new Node(null, null);

    private long hits = 0;
    private long misses = 0;
    private long evictions = 0;

    public LruCacheImpl(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public synchronized V get(K key) {
        Node node = cache.get(key);
        if (node == null) {
            misses++;
            return null;
        }
        hits++;
        moveToFront(node);
        return node.value;
    }

    @Override
    public synchronized void put(K key, V value) {
        Node node = cache.get(key);

        if (node != null) {
            node.value = value;
            moveToFront(node);
            return;
        }

        if (size == capacity) {
            evictLeastRecentlyUsed();
        }

        Node newNode = new Node(key, value);
        cache.put(key, newNode);
        addAfterHead(newNode);
        size++;
    }

    @Override
    public synchronized int size() {
        return size;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public long hits() {
        return hits;
    }

    @Override
    public long misses() {
        return misses;
    }

    @Override
    public long evictions() {
        return evictions;
    }

    private void moveToFront(Node node) {
        removeNode(node);
        addAfterHead(node);
    }

    private void addAfterHead(Node node) {
        Node first = head.next;
        head.next = node;
        node.prev = head;
        node.next = first;
        first.prev = node;
    }

    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
    }

    private void evictLeastRecentlyUsed() {
        Node lru = tail.prev;
        removeNode(lru);
        cache.remove(lru.key);
        size--;
        evictions++;
    }
}

