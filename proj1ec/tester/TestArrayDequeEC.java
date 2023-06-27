package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {

    @Test
    public void randomCallTest() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < 1000; i += 1) {
            int operator = StdRandom.uniform(0, 4);
            int randVal = StdRandom.uniform(100);
            if (operator == 0) {
                sad.addFirst(randVal);
                ads.addFirst(randVal);
                msg.append("addFirst(" + randVal + ")\n");
                assertEquals(msg.toString(), sad.size(), ads.size());
            } else if (operator == 1) {
                sad.addLast(randVal);
                ads.addLast(randVal);
                msg.append("addLast(" + randVal + ")\n");
                assertEquals(msg.toString(), sad.size(), ads.size());
            } else if (!sad.isEmpty()) {
                if (operator == 2) {
                    Integer val1 = sad.removeFirst();
                    Integer val2 = ads.removeFirst();
                    msg.append("removeFirst()\n");
                    assertEquals(msg.toString(), val1, val2);
                } else if (operator == 3) {
                    Integer val1 = sad.removeLast();
                    Integer val2 = ads.removeLast();
                    msg.append("removeLast()\n");
                    assertEquals(msg.toString(), val1, val2);
                }
            }
        }
    }
}
