package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesTwo() {
        IntList lst = IntList.of(14, 7, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 49 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesLast() {
        IntList lst = IntList.of(14, 15, 16, 20, 19);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 20 -> 361", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesFirst() {
        IntList lst = IntList.of(19, 15, 16, 20, 14);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("361 -> 15 -> 16 -> 20 -> 14", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesAll() {
        IntList lst = IntList.of(7, 13, 17, 19, 23);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("49 -> 169 -> 289 -> 361 -> 529", lst.toString());
        assertTrue(changed);
    }
}
