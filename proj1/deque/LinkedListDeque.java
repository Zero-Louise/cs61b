package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {

    private Node sentinel;
    private int size;


    private class Node {
        private Node prev;
        private T item;
        private Node next;

        Node(T item) {
            this.prev = null;
            this.item = item;
            this.next = null;
        }
    }

    public LinkedListDeque() {
        sentinel = new Node(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**
     * Adds an item of type Item to the front of the deque.
     * You can assume that item is never null.
     *
     * @param item
     */
    @Override
    public void addFirst(T item) {
        Node newNode = new Node(item);
        sentinel.next.prev = newNode;
        newNode.prev = sentinel;
        newNode.next = sentinel.next;
        sentinel.next = newNode;
        size += 1;
    }

    /**
     * Adds an item of type Item to the back of the deque.
     * You can assume that item is never null
     *
     * @param item
     */
    @Override
    public void addLast(T item) {
        Node newNode = new Node(item);
        sentinel.prev.next = newNode;
        newNode.prev = sentinel.prev;
        newNode.next = sentinel;
        sentinel.prev = newNode;
        size += 1;
    }

    /**
     * Returns the number of items in the deque.
     *
     * @return
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last,separated
     * by a space. Once all the items have been printed,print out a new line.
     */
    @Override
    public void printDeque() {
        if (isEmpty()) {
            return;
        }
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     *
     * @return
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T firstItem;
        Node firstNode = sentinel.next;
        firstItem = firstNode.item;
        sentinel.next = firstNode.next;
        firstNode.next.prev = sentinel;
        size -= 1;
        return firstItem;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     *
     * @return
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T lastItem;
        Node lastNode = sentinel.prev;
        lastItem = lastNode.item;
        sentinel.prev = lastNode.prev;
        lastNode.prev.next = sentinel;
        size -= 1;
        return lastItem;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is
     * the next item, and so forth. If no such item exists, returns null.
     * Must not alter the deque!
     *
     * @param index
     * @return
     */
    @Override
    public T get(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            return null;
        }
        Node p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is
     * the next item, and so forth. If no such item exists, returns null.
     * Must not alter the deque!
     *
     * @param index
     * @return
     */
    public T getRecursive(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            return null;
        }
        return getRecursive(index, sentinel.next);
    }

    /**
     * A helper function for getRecursive()
     *
     * @param index
     * @param p
     * @return
     */
    private T getRecursive(int index, Node p) {
        if (index == 0) {
            return p.item;
        }
        return getRecursive(index - 1, p.next);
    }

    /**
     * Return an iterator for LinkedListDeque
     *
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node p;

        LinkedListDequeIterator() {
            p = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return p != sentinel;
        }

        @Override
        public T next() {
            T item = p.item;
            p = p.next;
            return item;
        }
    }

    /**
     * Returns whether or not the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the
     * same contents (as goverened by the generic T’s equals method)
     * in the same order.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Deque)) {
            return false;
        }
        Deque<T> obj = (Deque<T>) o;
        if (this.size() != obj.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!this.get(i).equals(obj.get(i))) {
                return false;
            }
        }
        return true;
    }


}
