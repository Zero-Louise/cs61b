package randomizedtest;


import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE

    /**
     * Adds the same value to both the correct and buggy AList
     * implementations, then checks that the results of three
     * subsequent removeLast calls are the same
     */
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> aListNoResizing = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();

        aListNoResizing.addLast(4);
        aListNoResizing.addLast(5);
        aListNoResizing.addLast(6);

        buggyAList.addLast(4);
        buggyAList.addLast(5);
        buggyAList.addLast(6);

        // all should be true
        assertEquals(aListNoResizing.removeLast(), buggyAList.removeLast());
        assertEquals(aListNoResizing.removeLast(), buggyAList.removeLast());
        assertEquals(aListNoResizing.removeLast(), buggyAList.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> aListNoResizing = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                aListNoResizing.addLast(randVal);
                buggyAList.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size1 = aListNoResizing.size();
                System.out.println("size1: " + size1);
                int size2 = buggyAList.size();
                System.out.println("size2: " + size2);
                assertEquals(size1,size2);
            } else if (aListNoResizing.size() != 0) {
                if (operationNumber == 2) {
                    int lastVal1 = aListNoResizing.getLast();
                    System.out.println("getLast1(" + lastVal1 + ")");
                    int lastVal2 = buggyAList.getLast();
                    System.out.println("getLast2(" + lastVal2 + ")");
                    assertEquals(lastVal1,lastVal2);
                } else if (operationNumber == 3) {
                    int removeVal1 = aListNoResizing.removeLast();
                    System.out.println("removeLast1(" + removeVal1 + ")");
                    int removeVal2 = buggyAList.removeLast();
                    System.out.println("removeLast2(" + removeVal2 + ")");
                    assertEquals(removeVal1,removeVal2);
                }
            }
        }
    }
}
