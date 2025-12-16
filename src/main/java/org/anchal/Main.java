package org.anchal;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        LruCache<Integer, String> cache = new LruCacheImpl<>(2);

        cache.put(1, "A");
        cache.put(2, "B");

        System.out.println(cache.get(1));

        cache.put(3, "C");

        System.out.println(cache.get(2));
        System.out.println(cache.get(3));

        System.out.println("Size: " + cache.size());
        System.out.println("Hits: " + cache.hits());
        System.out.println("Misses: " + cache.misses());
        System.out.println("Evictions: " + cache.evictions());
    }
}
