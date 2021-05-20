package ru.rostanin.polyclinic.datastructures;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class HashTable<K extends Comparable<K>, T> {

    public static class Entry<K extends Comparable<K>, T> {
        public K key;
        public T value;
        public Entry(K key, T value) {
            this.key = key;
            this.value = value;
        }
    }

    private static final int DEFAULT_CAPACITY = 40;
    private static final double DEFAULT_LOAD_FACTOR = 0.65;
    private static final int HASH_CONSTANT = 31;
    private static final int LINEAR_CONSTANT = 17;

    private Entry[] entries;

    private int size;
    private int capacity;

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    public HashTable(int size) {
        this.capacity = size;
        entries = new Entry[capacity];
    }

    public void put(K key, T value) {

        // if key is not unique (already stored)
        if (findHash(key) >= 0) return;

        int attempt = 0;
        int hash = hash(key);

        // finding empty bucket
        while (entries[hash] != null) {
            hash = linearProbing(key, attempt++);
        }

        // put new element
        if (entries[hash] == null) {
            entries[hash] = new Entry(key, value);
            size++;
        }

        // calc load factor
        double loadFactor = (1.0 * size) / capacity;

        // rehash if necessary
        if (loadFactor > DEFAULT_LOAD_FACTOR) {
            rehash();
        }
    }

    // method to update value by key
    public boolean update(K key, T value) {
        int hash = findHash(key);
        if (hash < 0) return false;

        entries[hash].value = value;
        return true;
    }

    // method to remove value by key
    public void remove(K key) {

        int hash = findHash(key);
        if (hash < 0) return;

        entries[hash] = null;
        size--;
    }

    public T get(K key) {
        int hash = findHash(key);
        if (hash < 0) return null;

        return (T) entries[hash].value;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++)
            entries[i] = null;

        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
    }

    // returns list representation
    public List<T> list() {

        List<T> list = new ArrayList<>();

        for (int i = 0; i < capacity; i++) {
            if (entries[i] != null) {
                list.add((T) entries[i].value);
            }
        }

        return list;
    }

    // calc hash by key
    private int hash(K key) {

        if (key instanceof String) {

            int hash = 0;
            String stringKey = (String) key;

            for (int i = 0; i < stringKey.length(); i++)
                hash += HASH_CONSTANT * hash + stringKey.charAt(i);

            return Math.abs(hash % capacity);
        }

        return Math.abs(key.hashCode() % capacity);
    }

    private int linearProbing(K key, int attempt) {
        return (hash(key) + LINEAR_CONSTANT * attempt) % capacity;
    }

    private void rehash() {
        capacity *= 2;
        size = 0;

        Entry<K, T>[] tmp = entries;
        entries = new Entry[capacity];

        for (Entry<K, T> entry : tmp) {
            if (entry != null) put(entry.key, entry.value);
        }
    }

    // helper method to find hash by key
    private int findHash(K key) {

        if (size == 0) return -1;

        int attempt = 0;
        int hash = hash(key);

        if (entries[hash] == null) return -1;

        // finding actual node
        while (!entries[hash].key.equals(key)) {
            hash = linearProbing(key, attempt++);

            if (entries[hash] == null) return -1;
        }

        if (entries[hash].key.equals(key)) return hash;

        return -1;
    }

}
