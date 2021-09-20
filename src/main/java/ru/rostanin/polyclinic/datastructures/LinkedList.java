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

    public T removeFirst() {
        if (isEmpty()) throw new RuntimeException("Empty list");

        T data = head.data;
        head = head.next;
        --size;

        if (isEmpty()) tail = null;

        else head.prev = null;

        return data;
    }

    public T removeLast() {
        if (isEmpty()) throw new RuntimeException("Empty list");

        T data = tail.data;
        tail = tail.prev;
        --size;

        if (isEmpty()) head = null;

        else tail.next = null;

        return data;
    }

    private T remove(Node<K, T> node) {
        if (node.prev == null) return removeFirst();
        if (node.next == null) return removeLast();

        node.next.prev = node.prev;
        node.prev.next = node.next;

        T data = node.data;

        node.data = null;
        node = node.prev = node.next = null;

        --size;

        return data;
    }

    public T removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException();
        }

        int i;
        Node<K, T> trav;

        if (index < size / 2) {
            for (i = 0, trav = head; i != index; i++) {
                trav = trav.next;
            }
        } else
            for (i = size - 1, trav = tail; i != index; i--) {
                trav = trav.prev;
            }

        return remove(trav);
    }

    public boolean remove(K key, T value) {
        Node<K, T> trav = head;

        if (key == null) {
            for (trav = head; trav != null; trav = trav.next) {
                if (trav.key == null) {
                    remove(trav);
                    return true;
                }
            }
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

    public int indexOf(K key) {
        int index = 0;
        Node<K, T> trav = head;

        if (key == null) {
            for (; trav != null; trav = trav.next, index++) {
                if (trav.key == null) {
                    return index;
                }
            }
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