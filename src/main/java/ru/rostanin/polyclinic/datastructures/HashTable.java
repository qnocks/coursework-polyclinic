package ru.rostanin.polyclinic.datastructures;

@SuppressWarnings("unchecked")
public class HashTable<K extends Comparable<K>, T> {

    public static class Entry<K extends Comparable<K>, T> {

        public K key;
        public T value;
        public Entry(K key, T value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    private static final int DEFAULT_CAPACITY = 50;
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

        if (loadFactor > DEFAULT_LOAD_FACTOR) {
            rehash();
        }
    }

    public void update(K key, T value) {
        int hash = findHash(key);
        if (hash < 0) return;

        entries[hash].value = value;
    }

    public void remove(K key) {
        int hash = findHash(key);
        if (hash < 0) return;

        entries[hash] = null;
        size--;
    }

    public T get(K key) {
        int hash = findHash(key);
        if (hash < 0) return null;

        return (T) entries[hash];
    }

    public void clear() {
        for (int i = 0; i < capacity; i++)
            entries[i] = null;

        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
    }

    private int hash(K key) {

        if (key instanceof String) {

            int hash = 0;
            String stringKey = (String) key;

            for (int i = 0; i < stringKey.length(); i++)
                hash = HASH_CONSTANT * hash + stringKey.charAt(i);

            return hash % capacity;

        } else
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
        int attempt = 0;
        int hash = hash(key);

// finding non-empty bucket
        while (entries[hash] == null) {
            hash = linearProbing(key, attempt++);
        }

        if (entries[hash].key.equals(key)) return hash;

        return -1;
    }

}
