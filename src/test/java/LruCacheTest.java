import org.anchal.LruCacheImpl;
import org.junit.jupiter.api.Test;
import org.anchal.LruCache;

import static org.junit.jupiter.api.Assertions.*;

class LruCacheTest {

    @Test
    void testBasicPutAndGet() {
        LruCache<Integer, String> cache = new LruCacheImpl<>(2);
        cache.put(1, "A");
        assertEquals("A", cache.get(1));
    }

    @Test
    void testEvictionOrder() {
        LruCache<Integer, String> cache = new LruCacheImpl<>(2);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C"); // evict 1

        assertNull(cache.get(1));
        assertEquals("B", cache.get(2));
        assertEquals("C", cache.get(3));
    }

    @Test
    void testUpdateMovesToMRU() {
        LruCache<Integer, String> cache = new LruCacheImpl<>(2);
        cache.put(1, "A");
        cache.put(2, "B");

        cache.put(1, "A1"); // update key 1
        cache.put(3, "C");  // should evict key 2

        assertNull(cache.get(2));
        assertEquals("A1", cache.get(1));
    }

    @Test
    void testGetUpdatesMRU() {
        LruCache<Integer, String> cache = new LruCacheImpl<>(2);
        cache.put(1, "A");
        cache.put(2, "B");

        cache.get(1);       // make key 1 MRU
        cache.put(3, "C");  // evict key 2

        assertNull(cache.get(2));
        assertEquals("A", cache.get(1));
    }

    @Test
    void testCapacityOneEdgeCase() {
        LruCache<Integer, String> cache = new LruCacheImpl<>(1);
        cache.put(1, "A");
        cache.put(2, "B");

        assertNull(cache.get(1));
        assertEquals("B", cache.get(2));
        assertEquals(1, cache.size());
    }

    @Test
    void testConcurrency() throws InterruptedException {
        LruCache<Integer, Integer> cache = new LruCacheImpl<>(5);

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                cache.put(i % 10, i);
                cache.get(i % 10);
            }
        };

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        assertTrue(cache.size() <= cache.capacity());
    }
}