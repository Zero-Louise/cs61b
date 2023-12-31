package deque;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Performs some basic linked list tests.
 */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {


        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double> lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* Check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }


    @Test
    /* Check if method get() works */
    public void getItemTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();

        //should be null
        assertNull(lld1.get(0));
        assertNull(lld1.get(1));
        //should be null
        assertNull(lld1.getRecursive(0));
        assertNull(lld1.getRecursive(2));

        lld1.addFirst("first");
        lld1.addFirst("second");
        lld1.addFirst("third");

        lld1.printDeque();

        //should be null
        assertNull(lld1.get(-1));
        assertNull(lld1.get(5));
        //should be null
        assertNull(lld1.getRecursive(-1));
        assertNull(lld1.getRecursive(4));

        //should get the correct item
        assertEquals("first", lld1.get(2));
        assertEquals("second", lld1.get(1));
        assertEquals("third", lld1.get(0));

        assertEquals("first", lld1.getRecursive(2));
        assertEquals("second", lld1.getRecursive(1));
        assertEquals("third", lld1.getRecursive(0));

        //should not change the deque item
        lld1.printDeque();
    }

    @Test
    public void equalsTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld3 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld4 = new LinkedListDeque<>();

        lld1.addLast(3);
        lld1.addLast(5);
        lld1.addLast(7);

        lld2.addLast(3);
        lld2.addLast(5);
        lld2.addLast(7);

        //should be false
        assertEquals(lld1, lld2);
        assertEquals(lld2, lld1);

        lld3 = lld1;
        lld4 = lld1;

        //should be true
        assertEquals(lld1, lld3);
        assertEquals(lld3, lld4);
        assertEquals(lld1, lld4);


        LinkedListDeque<Integer> lld5 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld6 = new LinkedListDeque<>();

        Integer a = 2;
        Integer b = 4;
        Integer c = 6;

        lld5.addLast(a);
        lld5.addLast(b);
        lld5.addLast(c);

        lld6.addLast(a);
        lld6.addLast(b);
        lld6.addLast(c);
        lld6.addLast(4);

        //should be true
        assertNotEquals(lld5, lld6);
        assertNotEquals(lld6, lld5);

        LinkedListDeque<Integer> lld7 = new LinkedListDeque<>();

        lld6.addLast(a);
        lld6.addLast(b);
        lld6.addLast(b);

        //should be true
        assertNotEquals(lld5, lld7);
        assertNotEquals(lld7, lld5);

        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(a);
        ad1.addLast(b);
        ad1.addLast(c);

        assertTrue(ad1.equals(lld5));
        assertTrue(lld5.equals(ad1));
    }

    @Test
    public void iteratorTest(){
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();

        lld1.addLast("first");
        lld1.addLast("second");
        lld1.addLast("third");

        lld1.printDeque();

        for (String item:lld1){
            System.out.print(item + " ");
        }
    }
}
