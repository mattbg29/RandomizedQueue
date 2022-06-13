/* *****************************************************************************
 *  Code behind a randomized queue
 *  This is the solution to a Princeton Algorithms course question.  For more on
 *  this question and some of the code which is leveraged, here, see: https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php
 *  The difference between a randomized queue and a standard queue is the former will
 *  present a random finding in a for each loop, instead of iterating in order.
 *  In main, I have a simple example of some nested loops with queues and dequeues (the latter is also chosen at random).
 *  When you run the program, you will need to enter into the command-line some ints or Strings (must be all of the same type, however).
 *  Once you are finished entering the input, click control-d for the program to register your input and continue.
 **************************************************************************** */


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INIT_CAPACITY = 8;

    private Item[] a;
    private int n;

    public RandomizedQueue() {
        a = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;

        Item[] copy = (Item[]) new Object[capacity];
        int i = 0;
        int j = 0;
        while (i < a.length) {
            if (a[i] != null) {
                copy[j] = a[i];
                j++;
            }
            i++;
        }
        a = copy;
    }

    public void enqueue(Item item) {
        if (n == a.length) {
            resize(2 * a.length);
        }
        a[n++] = item;
    }


    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int rand = (int) Math.floor(Math.random() * (n));
        while (a[rand] == null) {
            rand = (int) Math.floor(Math.random() * (n));
        }
        Item item = a[rand];
        a[rand] = null;
        n--;
        if (n > 0 && n == a.length / 4) {
            resize(a.length / 2);
        }
        return item;
    }

    public Item sample() {
        int rand = (int) Math.floor(Math.random() * (n));
        Item item = a[rand];
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int i;
        private int counted;
        private Item[] b;

        public ListIterator() {
            b = (Item[]) new Object[a.length];
            for (int j = 0; j < a.length; j++) {
                b[j] = a[j];
            }
            StdRandom.shuffle(b);
            i = 0;
            counted = 0;
        }

        public boolean hasNext() {
            return counted < n;
        }

        public Item next() {
            Item itemNow = b[i];
            while (itemNow == null) {
                i++;
                itemNow = b[i];
            }
            i++;
            counted++;
            return itemNow;
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String wordNow = StdIn.readString();
            queue.enqueue(wordNow);
        }

        for (String s : queue) {
            for (String t : queue) {
                System.out.print(s + ":" + t + " ");
            }
            System.out.println();
        }
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        for (String s : queue) {
            for (String t : queue) {
                System.out.print(s + ":" + t + " ");
            }
            System.out.println();
        }
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        for (String s : queue) {
            for (String t : queue) {
                System.out.print(s + ":" + t + " ");
            }
            System.out.println();
        }
    }
}


