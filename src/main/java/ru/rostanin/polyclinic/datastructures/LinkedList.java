package ru.rostanin.polyclinic.datastructures;

public class LinkedList<K extends Comparable<K>, T> implements Iterable<T> {
    private int size = 0;
    private Node<K, T> head = null;
    private Node<K, T> tail = null;

    public void shakerSort() {

        boolean swapped = true;
        var start = head;
        var end = tail;
        if (head == null || tail == null) {
            return;
        }

        while (swapped) {

            swapped = false;

            var curr = start;
            var index = curr.next;

            while (curr != end.prev.prev) {
                if (curr.key.compareTo(index.key) > 0) {
                    var tmp = curr;
                    curr.key = index.key; curr.data = index.data;
                    index.key = tmp.key; index.data = tmp.data;
                    swapped = true;
                }
            }

            if (!swapped) break;

            swapped = false;
            end = end.prev;
            curr = end.prev;
            index = curr.prev;

            while (curr == start) {
                if (curr.key.compareTo(index.key) > 0) {
                    var tmp = curr;
                    curr = index;
                    index = tmp;
                    swapped = true;
                }
            }
            start = start.next;
        }
    }

    public void sort() {
        // Node current will point to head
        Node<K, T> current = head, index = null;
        Node<K, T> temp = null;
        if (head == null) {
            return;
        }
        while (current != null) {
            // Node index will point to node next to
            // current
            index = current.next;
            while (index != null) {
                // If current node's data is greater
                // than index's node data, swap the data
                // between them
                if (current.key.compareTo(index.key) > 0) {
//                    temp = current;
                    temp = new Node<>(current.key, current.data, null, null);
//                    current = index;
                    current.key = index.key; current.data = index.data;
//                    index = temp;
                    index.key = temp.key; index.data = temp.data;
                }
                index = index.next;
            }
            current = current.next;
        }
    }
    // Internal node class to represent data
    private static class Node<K extends Comparable<K>, T> {
        private K key;
        private T data;

        private Node<K, T> prev, next;

        public Node(K key, T data, Node<K, T> prev, Node<K, T> next) {
            this.key = key;
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
        @Override
        public String toString() {
            return data.toString();
        }

    }
    // Empty this linked list, O(n)

    public void clear() {
        Node<K, T> trav = head;
        while (trav != null) {
            Node<K, T> next = trav.next;
            trav.prev = trav.next = null;
            trav.data = null;
            trav = next;
        }
        head = tail = trav = null;
        size = 0;
    }
    // Return the size of this linked list

    public int size() {
        return size;
    }
    // Is this linked list empty?

    public boolean isEmpty() {
        return size() == 0;
    }
    // Add an element to the tail of the linked list, O(1)

    public void update(K key, T elem) {
        if (!contains(key)) return;

        var curr = head;

        while (curr != null) {
            if (curr.key.equals(key)) {
                curr.data = elem;
            }
            curr = curr.next;
        }
    }

    public void add(K key, T elem) {
        addLast(key, elem);
        sort();
    }

    // Add a node to the tail of the linked list, O(1)

    public void addLast(K key, T elem) {
        if (isEmpty()) {
            head = tail = new Node<K, T>(key, elem, null, null);
        } else {
            tail.next = new Node<K, T>(key, elem, tail, null);
            tail = tail.next;
        }
        size++;
    }
    // Add an element to the beginning of this linked list, O(1)

    public void addFirst(K key, T elem) {
        if (isEmpty()) {
            head = tail = new Node<K, T>(key, elem, null, null);
        } else {
            head.prev = new Node<K, T>(key, elem, null, head);
            head = head.prev;
        }
        size++;
    }
    // Add an element at a specified index

    public void addAt(int index, K key, T data) throws Exception {
        if (index < 0) {
            throw new Exception("Illegal Index");
        }
        if (index == 0) {
            addFirst(key, data);
            return;
        }

        if (index == size) {
            addLast(key, data);
            return;
        }

        Node<K, T> temp = head;
        for (int i = 0; i < index - 1; i++) {
            temp = temp.next;
        }
        Node<K, T> newNode = new Node<>(key, data, temp, temp.next);
        temp.next.prev = newNode;
        temp.next = newNode;

        size++;
    }

    // Check the value of the first node if it exists, O(1)
    public T peekFirst() {
        if (isEmpty()) throw new RuntimeException("Empty list");
        return head.data;
    }

    // Check the value of the last node if it exists, O(1)
    public T peekLast() {
        if (isEmpty()) throw new RuntimeException("Empty list");
        return tail.data;
    }

    // TODO: Do I need it?
    // Remove the first value at the head of the linked list, O(1)
    public T removeFirst() {
        // Can't remove data from an empty list
        if (isEmpty()) throw new RuntimeException("Empty list");

        // Extract the data at the head and move
        // the head pointer forwards one node
        T data = head.data;
        head = head.next;
        --size;

        // If the list is empty set the tail to null
        if (isEmpty()) tail = null;

            // Do a memory cleanup of the previous node
        else head.prev = null;

        // Return the data that was at the first node we just removed
        return data;
    }

    // Remove the last value at the tail of the linked list, O(1)
    public T removeLast() {
        // Can't remove data from an empty list
        if (isEmpty()) throw new RuntimeException("Empty list");

        // Extract the data at the tail and move
        // the tail pointer backwards one node
        T data = tail.data;
        tail = tail.prev;
        --size;

        // If the list is now empty set the head to null
        if (isEmpty()) head = null;

            // Do a memory clean of the node that was just removed
        else tail.next = null;

        // Return the data that was in the last node we just removed
        return data;
    }

    // Remove an arbitrary node from the linked list, O(1)
    private T remove(Node<K, T> node) {
        // If the node to remove is somewhere either at the
        // head or the tail handle those independently
        if (node.prev == null) return removeFirst();
        if (node.next == null) return removeLast();

        // Make the pointers of adjacent nodes skip over 'node'
        node.next.prev = node.prev;
        node.prev.next = node.next;

        // Temporarily store the data we want to return
        T data = node.data;

        // Memory cleanup
        node.data = null;
        node = node.prev = node.next = null;

        --size;

        // Return the data in the node we just removed
        return data;
    }

    // Remove a node at a particular index, O(n)
    public T removeAt(int index) {
        // Make sure the index provided is valid
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException();
        }

        int i;
        Node<K, T> trav;

        // Search from the front of the list
        if (index < size / 2) {
            for (i = 0, trav = head; i != index; i++) {
                trav = trav.next;
            }
            // Search from the back of the list
        } else
            for (i = size - 1, trav = tail; i != index; i--) {
                trav = trav.prev;
            }

        return remove(trav);
    }

    // Remove a particular value in the linked list, O(n)
    public boolean remove(K key, T value) {
        Node<K, T> trav = head;

        // Support searching for null
        if (key == null) {
            for (trav = head; trav != null; trav = trav.next) {
                if (trav.key == null) {
                    remove(trav);
                    return true;
                }
            }
            // Search for non null object
        } else {
            for (trav = head; trav != null; trav = trav.next) {
                if (key.equals(trav.key)) {
                    remove(trav);
                    return true;
                }
            }
        }
        return false;
    }

    // Find the index of a particular value in the linked list, O(n)
    public int indexOf(K key) {
        int index = 0;
        Node<K, T> trav = head;

        // Support searching for null
        if (key == null) {
            for (; trav != null; trav = trav.next, index++) {
                if (trav.key == null) {
                    return index;
                }
            }
            // Search for non null object
        } else
            for (; trav != null; trav = trav.next, index++) {
                if (key.equals(trav.key)) {
                    return index;
                }
            }

        return -1;
    }

    // Check is a value is contained within the linked list
    public boolean contains(K key) {
        return indexOf(key) != -1;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private Node<K, T> trav = head;

            @Override
            public boolean hasNext() {
                return trav != null;
            }

            @Override
            public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        Node<K, T> trav = head;
        while (trav != null) {
            sb.append(trav.data);
            if (trav.next != null) {
                sb.append(", ");
            }
            trav = trav.next;
        }
        sb.append(" ]");
        return sb.toString();
    }
}