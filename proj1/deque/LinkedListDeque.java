package deque;

public class LinkedListDeque<Item> {

    private Node sentinel;
    private int size;

    private class Node {
        private Node prev;
        private Item item;
        private Node next;

        public Node(Item item) {
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
    public void addFirst(Item item) {
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
    public void addLast(Item item) {
        Node newNode = new Node(item);
        sentinel.prev.next = newNode;
        newNode.prev = sentinel.prev;
        newNode.next = sentinel;
        sentinel.prev = newNode;
        size += 1;
    }

    /**
     * Returns true if deque is empty, false otherwise
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last,separated
     * by a space. Once all the items have been printed,print out a new line.
     */
    public void printDeque() {
        if (size == 0) {
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
    public Item removeFirst() {
        if (size == 0) {
            return null;
        }
        Item firstItem;
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
    public Item removeLast() {
        if (size == 0) {
            return null;
        }
        Item lastItem;
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
    public Item get(int index) {
        if (size == 0 || index < 0 || index >= size) {
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
    public Item getRecursive(int index) {
        if (size == 0 || index < 0 || index >= size) {
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
    private Item getRecursive(int index, Node p) {
        if (index == 0) {
            return p.item;
        }
        return getRecursive(index - 1, p.next);
    }
}
