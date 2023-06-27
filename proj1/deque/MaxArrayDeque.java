package deque;

import java.util.Comparator;


public class MaxArrayDeque<T> extends ArrayDeque {

    private Comparator<T> comparator;

    /**
     * Creates a MaxArrayDeque with the given Comparator.
     *
     * @param c
     */
    public MaxArrayDeque(Comparator<T> c) {
        this.comparator = c;
    }

    /**
     * Returns the maximum element in the deque as governed
     * by the previously given Comparator.
     * If the MaxArrayDeque is empty, simply return null.
     *
     * @return
     */
    public T max() {
        return max(comparator);
    }

    /**
     * Returns the maximum element in the deque
     * as governed by the parameter Comparator c.
     * If the MaxArrayDeque is empty, simply return null.
     *
     * @param c
     * @return
     */
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T max = (T) get(0);
        for (int i = 1; i < size(); i += 1) {
            if (c.compare(max, (T) get(i)) < 0) {
                max = (T) get(i);
            }
        }
        return max;
    }
}
