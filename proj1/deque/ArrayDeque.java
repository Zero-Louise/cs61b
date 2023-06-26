package deque;

public class ArrayDeque<Item> {


    private Item[] items;
    private int size;

    private int firstIndex;

    private int lastIndex;

    private static final int INITIATE_SIZE = 8;

    private static final double RESIZE_FACTOR = 0.25;

    public ArrayDeque() {
        items = (Item[]) new Object[INITIATE_SIZE];
        firstIndex = 0;
        lastIndex = 0;
        size = 0;
    }

    /**
     * Adds an item of type Item to the front of the deque.
     * You can assume that item is never null.
     *
     * @param item
     */
    public void addFirst(Item item) {
        if (size != 0) {
            firstIndex -= 1;
            checkIndexValid();
        }
        items[firstIndex] = item;
        size += 1;
        checkResize();
    }

    /**
     * Adds an item of type Item to the back of the deque.
     * You can assume that item is never null
     *
     * @param item
     */
    public void addLast(Item item) {
        if (size != 0) {
            lastIndex += 1;
            checkIndexValid();
        }
        items[lastIndex] = item;
        size += 1;
        checkResize();
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
        int first = firstIndex;
        int last = lastIndex;
        if (first > last) {
            for (int i = first; i <= items.length - 1; i += 1) {
                System.out.print(items[i] + " ");
            }
            for (int i = 0; i <= last; i += 1) {
                System.out.print(items[i] + " ");
            }
        } else {
            for (int i = first; i <= last; i += 1) {
                System.out.print(items[i] + " ");
            }
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
        Item rItem = items[firstIndex];
        if (size != 1) {
            firstIndex += 1;
        }
        checkIndexValid();
        size -= 1;
        return rItem;
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
        Item rItem = items[lastIndex];
        if (size != 1) {
            lastIndex -= 1;
        }
        checkIndexValid();
        size -= 1;
        return rItem;
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
        int length = items.length;
        if (size == 0 || index < 0 || index >= length) {
            return null;
        }
        return items[index];
    }

    private boolean checkResize() {
        return false;
    }

    private void resize() {

    }

    private void checkIndexValid() {
        int length = items.length;
        if (firstIndex < 0) {
            firstIndex = length - 1;
        }
        if (firstIndex >= length) {
            firstIndex = 0;
        }
        if (lastIndex < 0) {
            lastIndex = length - 1;
        }
        if (lastIndex >= length) {
            lastIndex = 0;
        }
    }
}
