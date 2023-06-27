package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {

    private T[] items;
    private int size;

    private int firstIndex;

    private int lastIndex;

    private static final int INITIATE_SIZE = 8;

    private static final double RESIZE_FACTOR = 0.25;

    public ArrayDeque() {
        items = (T[]) new Object[INITIATE_SIZE];
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
    @Override
    public void addFirst(T item) {
        if (!isEmpty()) {
            firstIndex = (firstIndex - 1 + getItemsLength()) % getItemsLength();
        }
        items[firstIndex] = item;
        size += 1;
        checkResize(0);
    }

    /**
     * Adds an item of type Item to the back of the deque.
     * You can assume that item is never null
     *
     * @param item
     */
    @Override
    public void addLast(T item) {
        if (!isEmpty()) {
            lastIndex = (getItemsLength() + lastIndex + 1) % getItemsLength();
        }
        items[lastIndex] = item;
        size += 1;
        checkResize(0);
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
        /*
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
        */
        for (int i = 0; i < size; i += 1) {
            System.out.print(get(i) + " ");
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
        checkResize(1);
        if (isEmpty()) {
            return null;
        }
        T rItem = items[firstIndex];
        if (size != 1) {
            firstIndex = (getItemsLength() + firstIndex + 1) % getItemsLength();
        }
        size -= 1;
        return rItem;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     *
     * @return
     */
    @Override
    public T removeLast() {
        checkResize(1);
        if (isEmpty()) {
            return null;
        }
        T rItem = items[lastIndex];
        if (size != 1) {
            lastIndex = (lastIndex - 1 + getItemsLength()) % getItemsLength();
        }
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
    @Override
    public T get(int index) {
        int length = items.length;
        if (isEmpty() || index < 0 || index >= size) {
            return null;
        }
        return items[(firstIndex + index + length) % length];
    }

    /**
     * Check if the array need the be resized
     *
     * @param op
     */
    private void checkResize(int op) {
        int length = items.length;
        double ratio = (double) size / length;
        if (size == length || (ratio < RESIZE_FACTOR && length >= 16)) {
            resize(op);
        }
    }

    /**
     * Resize the array
     *
     * @param op 0 is meaning that expand the array, 1 is meaning that reduce the array
     */
    private void resize(int op) {
        T[] newItems;
        int length = items.length;
        if (op == 0) {
            newItems = (T[]) new Object[length * 2];
            if (firstIndex > lastIndex) {
                System.arraycopy(items, firstIndex, newItems, firstIndex + length, length - firstIndex);
                System.arraycopy(items, 0, newItems, 0, lastIndex + 1);
                firstIndex += length;
            } else {
                System.arraycopy(items, 0, newItems, 0, length);
            }
        } else {
            int newLength = length / 2;
            newItems = (T[]) new Object[newLength];
            if (firstIndex >= newLength) {
                if (lastIndex >= newLength) {
                    System.arraycopy(items, firstIndex, newItems, firstIndex - newLength, lastIndex - firstIndex + 1);
                    lastIndex -= newLength;
                } else {
                    System.arraycopy(items, firstIndex, newItems, firstIndex - newLength, length - firstIndex);
                    System.arraycopy(items, 0, newItems, 0, lastIndex + 1);
                }
                firstIndex -= newLength;
            } else {
                if (lastIndex >= newLength) {
                    System.arraycopy(items, firstIndex, newItems, firstIndex, newLength - firstIndex);
                    System.arraycopy(items, newLength, newItems, 0, lastIndex - newLength + 1);
                    lastIndex -= newLength;
                } else {
                    System.arraycopy(items, firstIndex, newItems, firstIndex, lastIndex - firstIndex + 1);
                }
            }
        }
        items = newItems;
    }

    /**
     * Only for randomizedTest
     *
     * @return
     */
    private int getFirstIndex() {
        return firstIndex;
    }

    /**
     * Only for randomizedTest
     *
     * @return
     */
    private int getLastIndex() {
        return lastIndex;
    }

    /**
     * Only for randomizedTest
     *
     * @return
     */
    private int getItemsLength() {
        return items.length;
    }

    /**
     * Only for randomizedTest
     */
    private void printADequeStatus() {
        System.out.println("first:" + getFirstIndex() + " last:" + getLastIndex() + " size:" + size() + " length:" + getItemsLength());
        printDeque();
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
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        ArrayDeque<T> obj = (ArrayDeque<T>) o;
        if (this.size() != obj.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if (this.get(i) != obj.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return an iterator for ArrayDeque
     *
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {

        private int index;

        private int size;

        public ArrayDequeIterator() {
            index = 0;
            size = size();
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            T item = get(index);
            index += 1;
            return item;
        }
    }


    /**
     * Check if the array need the be resized
     *
     * @param op
     */
    /*
    private boolean checkResize(int op) {
        int length = items.length;
        double ratio = (double) size / length;
        if (ratio > RESIZE_FACTOR) {
            resize(op);
            System.out.println("sized");
            return true;
        }
        return false;
    }
     */

    /**
     * Resize the array
     *
     * @param op 0 is meaning that expand the array, 1 is meaning that reduce the array
     */
    /*
    private void resize(int op) {
        T[] newItems;
        int length = items.length;
        int first = firstIndex;
        int last = lastIndex;
        if (op == 0) {
            newItems = (T[]) new Object[length * 2];
            if (first > last) {
                System.arraycopy(items, first, newItems, first + length, length - first);
                System.arraycopy(items, 0, newItems, 0, last + 1);
                firstIndex += length;
            } else {
                System.arraycopy(items, first, newItems, first, last - first + 1);
            }
        } else {
            newItems = (T[]) new Object[length / 2];
            if (first > last) {
                if (first > length / 2) {
                    if (last > length / 2) {
                        System.arraycopy(items, last, newItems, last - (length) / 2, first - last + 1);
                        lastIndex -= length / 2;
                    } else {
                        System.arraycopy(items, first, newItems, first - (length / 2), length - first);
                        System.arraycopy(items, 0, newItems, 0, last + 1);
                    }
                    firstIndex -= length / 2;
                } else {
                    System.arraycopy(items, first, newItems, first, (length / 2) - first);
                    System.arraycopy(items, 0, newItems, 0, last + 1);
                }
            } else {
                if (first > length / 2) {
                    System.arraycopy(items, first, newItems, first - (length / 2), last - first + 1);
                    firstIndex -= length;
                    lastIndex -= length;
                } else {
                    if (last > length / 2) {
                        System.arraycopy(items, first, newItems, first, (length / 2) - first);
                        System.arraycopy(items, length / 2, newItems, 0, last - (length / 2) + 1);
                        lastIndex -= length;
                    } else {
                        System.arraycopy(items, first, newItems, first, last - first + 1);
                    }
                }
            }
        }
        items = newItems;
    }
    */

    /**
     * Check index is valid after modifying Deque item
     */
    /*
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
    */
}
