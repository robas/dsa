/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int[] index;
        private int current;

        public RandomizedQueueIterator() {
            current = 0;
            index = new int[n];
            for (int i = 0; i < n; i++) {
                index[i] = i;
            }
            for (int i = 0; i < n; i++) {
                swap(StdRandom.uniformInt(n), StdRandom.uniformInt(n));
            }
        }

        private void swap(int i, int j) {
            int tmp = index[i];
            index[i] = index[j];
            index[j] = tmp;
        }

        public boolean hasNext() {
            return current < n;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("there are no more items to return");
            }
            return q[index[current++]];
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "remove() method should not be called on Iterator");
        }
    }

    private Item[] q;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[1];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("cannot add null argument");
        }

        if (n == q.length) {
            resize(2 * q.length);
        }
        q[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot dequeue from empty RandomizedQueue");
        }
        int i = StdRandom.uniformInt(n);
        Item p = q[i];
        if (--n == 0) {
            return p;
        }
        q[i] = q[n];
        q[n] = null;

        if (n == q.length / 4) {
            resize(q.length / 2);
        }
        return p;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot sample from empty RandomizedQueue");
        }
        int i = StdRandom.uniformInt(n);
        return q[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resize(int capacity) {
        // StdOut.println("resizing...");
        Item[] newQ = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            newQ[i] = q[i];
        }
        q = newQ;
    }

    private void print() {
        StdOut.printf("\n[");
        for (int i = 0; i < n; i++) {
            StdOut.printf("%d,", q[i]);
        }
        StdOut.printf("]\n");
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        int n = 10;
        for (int i = 0; i < n; i++) {
            q.enqueue(i);
        }
        for (int a : q) {
            StdOut.printf("%d,", a);
        }

        StdOut.println("\n----");

        for (int i = 0; i < n; i++) {
            StdOut.println(q.sample());
        }
        StdOut.println("----");
        for (int i = 0; i < n; i++) {
            StdOut.println(q.dequeue());
            q.print();
        }
        StdOut.println("----");


    }

}
