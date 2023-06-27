package deque;

import edu.princeton.cs.algs4.StdRandom;
import net.sf.saxon.om.Item;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void addIsEmptySizeTest() {
        ArrayDeque<String> ad1 = new ArrayDeque<String>();

        assertTrue("A newly initialized ADeque should be empty", ad1.isEmpty());
        ad1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, ad1.size());
        assertFalse("ad1 should now contain 1 item", ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());

        ad1.addLast("back");
        assertEquals(3, ad1.size());
        ad1.addLast("back1");
        ad1.addLast("back2");
        ad1.addLast("back3");
        ad1.addFirst("front1");
        ad1.addLast("back4");

        System.out.println("Printing out deque: ");
        ad1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        // should be empty
        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());

        System.out.println("Printing out deque: ");

        ad1.addFirst(10);
        // should not be empty
        assertFalse("ad1 should contain 1 item", ad1.isEmpty());

        ad1.printDeque();

        ad1.removeFirst();
        // should be empty
        assertTrue("ad1 should be empty after removal", ad1.isEmpty());

        ad1.printDeque();
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(3);

        ad1.removeLast();
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeFirst();

        int size = ad1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {


        ArrayDeque<String> ad1 = new ArrayDeque<String>();
        ArrayDeque<Double> ad2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> ad3 = new ArrayDeque<Boolean>();

        ad1.addFirst("string");
        ad2.addFirst(3.14159);
        ad3.addFirst(true);

        String s = ad1.removeFirst();
        double d = ad2.removeFirst();
        boolean b = ad3.removeFirst();
    }

    @Test
    /* Check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {
        LinkedListDeque<Integer> ad1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, ad1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, ad1.removeLast());
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigADequeTest() {

        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            ad1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) ad1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) ad1.removeLast(), 0.0);
        }
    }


    @Test
    /* Check if method get() works */
    public void getItemTest() {
        ArrayDeque<String> ad1 = new ArrayDeque<>();

        //should be null
        assertNull(ad1.get(0));
        assertNull(ad1.get(1));
        //should be null

        ad1.addFirst("first");
        ad1.addFirst("second");
        ad1.addFirst("third");

        ad1.printDeque();

        //should be null
        assertNull(ad1.get(-1));
        assertNull(ad1.get(9));
        //should be null


        //should get the correct item
        assertEquals("third", ad1.get(0));
        assertEquals("second", ad1.get(1));
        assertEquals("first", ad1.get(2));

        //should not change the deque item
        ad1.printDeque();
    }

    /*
    @Test
    public void randomizedTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();

        int N = 10000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 8);
            System.out.println("opNum:" + operationNumber);
            //ad1.printADequeStatus();
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                ad1.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = ad1.size();
                System.out.println("size: " + size);
            } else if (operationNumber == 2) {
                //isEmpty
                boolean isEmpty = ad1.isEmpty();
                System.out.println("isEmpty: " + isEmpty);
            } else if (operationNumber == 3) {
                //addFirst
                int randVal = StdRandom.uniform(0, 100);
                ad1.addFirst(randVal);
                System.out.println("addFirst(" + randVal + ")");
            } else if (ad1.size() != 0) {
                if (operationNumber == 4) {
                    int randIndex = 0;
                    int firstIndex = ad1.getFirstIndex();
                    int lastIndex = ad1.getLastIndex();
                    int firstOrLast = StdRandom.uniform(0, 2);
                    int length = ad1.getItemsLength();
                    if (firstOrLast == 0) {
                        if (firstIndex > lastIndex) {
                            randIndex = StdRandom.uniform(firstIndex, length);
                        } else {
                            randIndex = StdRandom.uniform(firstIndex, lastIndex + 1);
                        }
                    } else {
                        if (firstIndex > lastIndex) {
                            randIndex = StdRandom.uniform(0, lastIndex + 1);
                        } else {
                            randIndex = StdRandom.uniform(firstIndex, lastIndex + 1);
                        }
                    }
                    int getVal = ad1.get(randIndex);
                    System.out.println("get(" + randIndex + ":" + getVal + ")");
                } else if (operationNumber == 5) {
                    ad1.printDeque();
                } else if (operationNumber == 6) {
                    //removeFirst
                    int reVal = ad1.removeFirst();
                    System.out.println("removeFirst(" + reVal + ")");
                } else if (operationNumber == 7) {
                    //removeLast
                    int reVal = ad1.removeLast();
                    System.out.println("removeLast(" + reVal + ")");
                }
            }
        }
    }
    */

    @Test
    public void iteratorTest(){
        ArrayDeque<String> ad1 = new ArrayDeque<>();

        ad1.addLast("first");
        ad1.addLast("second");
        ad1.addLast("third");
        ad1.addFirst("first2");

        ad1.printDeque();

        for (String item:ad1){
            System.out.print(item + " ");
        }
    }

    @Test
    public void equalsTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        ArrayDeque<Integer> ad3 = new ArrayDeque<>();
        ArrayDeque<Integer> ad4 = new ArrayDeque<>();

        ad1.addLast(3);
        ad1.addLast(5);
        ad1.addLast(7);

        ad2.addLast(3);
        ad2.addLast(5);
        ad2.addLast(7);

        //should be false
        assertEquals(ad1, ad2);
        assertEquals(ad2, ad1);

        ad3 = ad1;
        ad4 = ad1;

        //should be true
        assertEquals(ad1, ad3);
        assertEquals(ad3, ad4);
        assertEquals(ad1, ad4);


        LinkedListDeque<Integer> ad5 = new LinkedListDeque<>();
        LinkedListDeque<Integer> ad6 = new LinkedListDeque<>();

        Integer a = 2;
        Integer b = 4;
        Integer c = 6;

        ad5.addLast(a);
        ad5.addLast(b);
        ad5.addLast(c);

        ad6.addLast(a);
        ad6.addLast(b);
        ad6.addLast(c);
        ad6.addLast(4);

        //should be true
        assertNotEquals(ad5, ad6);
        assertNotEquals(ad6, ad5);

        LinkedListDeque<Integer> ad7 = new LinkedListDeque<>();

        ad6.addLast(a);
        ad6.addLast(b);
        ad6.addLast(b);

        //should be true
        assertNotEquals(ad5, ad7);
        assertNotEquals(ad7, ad5);
    }
}