package ru.rostanin.polyclinic.datastructures;

import java.util.ArrayList;
import java.util.List;

public class AvlTree<K extends Comparable<K>, T> {

    public static class Node<K extends Comparable<K>, T> {

        public int bf;
        public K key;
        public T value;
        public int height;
        public Node<K, T> left, right;

        public Node(K key, T value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, T> root;
    private int nodeCount = 0;

    public Node<K, T> getRoot() {
        return root;
    }

    public T contains(K key) {
        Node<K, T> node = root;
        while (node != null) {
            if (key.compareTo(node.key) < 0) node = node.left;
            else if (key.compareTo(node.key) > 0) node = node.right;
            else return node.value;
        }
        return null;
    }

    public boolean insert(K key, T value) {
        if (value == null || key == null) return false;
        if (contains(key) == null) {
            root = insert(root, key, value);
            nodeCount++;
            return true;
        }
        return false;
    }

    private Node<K, T> insert(Node<K, T> node, K key, T value) {

        if (node == null) return new Node<>(key, value);

        // Insert node in left subtree.
        if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, value);

            // Insert node in right subtree.
        } else if (key.compareTo(node.key) > 0) {
            node.right = insert(node.right, key, value);
        }

        // Update balance factor and height values.
        update(node);

        // Re-balance tree.
        return balance(node);
    }

    // Update a node's height and balance factor.
    private void update(Node<K, T> node) {

        int leftNodeHeight = (node.left == null) ? -1 : node.left.height;
        int rightNodeHeight = (node.right == null) ? -1 : node.right.height;

        // Update this node's height.
        node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);

        // Update balance factor.
        node.bf = rightNodeHeight - leftNodeHeight;
    }

    // Re-balance a node if its balance factor is +2 or -2.
    private Node<K, T> balance(Node<K, T> node) {

        // Left heavy subtree.
        if (node.bf == -2) {

            // Left-Left case.
            if (node.left.bf <= 0) {
                return leftLeftCase(node);

                // Left-Right case.
            } else {
                return leftRightCase(node);
            }

            // Right heavy subtree needs balancing.
        } else if (node.bf == +2) {

            // Right-Right case.
            if (node.right.bf >= 0) {
                return rightRightCase(node);

                // Right-Left case.
            } else {
                return rightLeftCase(node);
            }
        }
        // If node has a balance factor of 0, +1 or -1, then it's fine.
        return node;
    }

    private Node<K, T> leftLeftCase(Node<K, T> node) {
        return rightRotation(node);
    }

    private Node<K, T> leftRightCase(Node<K, T> node) {
        node.left = leftRotation(node.left);
        return leftLeftCase(node);
    }

    private Node<K, T> rightRightCase(Node<K, T> node) {
        return leftRotation(node);
    }

    private Node<K, T> rightLeftCase(Node<K, T> node) {
        node.right = rightRotation(node.right);
        return rightRightCase(node);
    }

    private Node<K, T> leftRotation(Node<K, T> node) {
        Node<K, T> newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        update(node);
        update(newParent);
        return newParent;
    }

    private Node<K, T> rightRotation(Node<K, T> node) {
        Node<K, T> newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        update(node);
        update(newParent);
        return newParent;
    }

    // Remove a value from this binary tree if it exists.
    public boolean remove(K key) {

        if (contains(key) != null) {
            root = remove(root, key);
            nodeCount--;
            return true;
        }

        return false;
    }

    // Removes a value from the AVL tree. If the value we're trying to remove
    // does not exist in the tree then the null is returned. Otherwise,
    // the new (or old) root node is returned.
    private Node<K, T> remove(Node<K, T> node, K key) {

        if (node == null) return null;

        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);

        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);

            // Finally found the value.
        } else {

            // This is the case with only a right subtree or no subtree at all.
            // In this situation just swap the node we wish to remove
            // with its right child.
            if (node.left == null) {
                return node.right;

                // This is the case with only a left subtree or
                // no subtree at all. In this situation just
                // swap the node we wish to remove with its left child.
            } else if (node.right == null) {
                return node.left;

                // This is the case with two subtrees.
            } else {

                // Choose to remove from left subtree
                if (node.left.height > node.right.height) {

                    // Swap the value of the successor into the node.
                    var successorNode = findMax(node.left);
                    node.key = successorNode.key;
                    node.value = successorNode.value;
                    node.left = remove(node.left, node.key);

                } else {

                    // Swap the value of the successor into the node.
                    var successorNode = findMin(node.right);
                    node.key = successorNode.key;
                    node.value = successorNode.value;
                    node.right = remove(node.right, node.key);
                }
            }
        }
        // Update balance factor and height values.
        update(node);
        // Re-balance tree.
        return balance(node);
    }

    // Method to find the rightmost node (which has the largest value).
    private Node<K, T> findMax(Node<K, T> node) {
        while (node.right != null) node = node.right;
        return node;
    }

    // Method to find the leftmost node (which has the smallest value).
    private Node<K, T> findMin(Node<K, T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // Method returns a list of the post-order tree values.
    public List<T> getListInPostOrder(Node<K, T> node) {
        var list = new ArrayList<T>();
        if (node != null) {
            if (node.left != null) {
                list.addAll(getListInPostOrder(node.left));
            }
            if (node.right != null) {
                list.addAll(getListInPostOrder(node.right));
            }
            list.add(node.value);
        }
        return list;
    }
}
